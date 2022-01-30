using FastCleanAPI.DTO_s.ChatDTO;
using FastCleanAPI.DTO_s.Cliente;
using FastCleanAPI.DTO_s.Funcionario;
using FastCleanAPI.DTO_s.Marcacao;
using FastCleanAPI.DTO_s.ReportsDTO;
using FastCleanAPI.DTO_s.Utilizador;
using FastCleanAPI.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;

namespace FastCleanAPI.Controllers
{
    [Route("api")]
    [ApiController]
    public class UserController : ControllerBase
    {

        private readonly FastCleanContext _context;



        /// <summary>
        /// Metodo Construtor
        /// </summary>
        /// <param name="context"></param>
        public UserController(FastCleanContext context)
        {
            _context = context;

        }

        /// <summary>
        /// Este controller vai possibilitar efetuar o Login de um Utilizador
        /// </summary>
        /// <param name="userInfo"></param>
        /// <returns>UserInfoResponse</returns>
        //POST: api/Login
        [HttpPost("Login")]
        [AllowAnonymous]
        public IActionResult Login(LoginModel userInfo)
        {
            Utilizador user = UserExist(userInfo.Email, userInfo.Password);


            ///Verifica se o user que vai efetuar o Login esta banido
            if (user.Banido == true)
            {
                return BadRequest("Este utilizador está banido, não pode efetuar login com esta conta.");
            }



            if (userInfo.Email != user.Email)
            {
                return NotFound("Este email não existe. Introduza o seu Email.");
            }



            if (BCrypt.Net.BCrypt.Verify(userInfo.Password, user.Password) == false)
            {
                return NotFound("Esta Password não existe. Introduza a sua Password.");
            }


            var tokenString = generateToken(user);

            UserInfoResponse userReturned;

            userReturned = new UserInfoResponse { Id = user.Id, Nome = user.Nome, Cargo = user.Cargo.ToString(), Email = user.Email, Morada = user.Morada, Token = tokenString };

            return Ok(userReturned);
        }

        /// <summary>
        /// Este metodo vai gerar um token, que vai se passado quando for efetuado o login
        /// </summary>
        /// <param name="UtilizadorInfo"></param>
        /// <returns> Token</returns>
        private string generateToken(Utilizador UtilizadorInfo)
        {
            var tokenHandler = new JwtSecurityTokenHandler();
            var key = Encoding.ASCII.GetBytes(Settings.Secret);

            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = new ClaimsIdentity(new Claim[] {

                    new Claim(ClaimTypes.Email, UtilizadorInfo.Email.ToString()),
                    new Claim(ClaimTypes.Role, UtilizadorInfo.Cargo.ToString()),
                    new Claim(ClaimTypes.NameIdentifier, UtilizadorInfo.Id.ToString()),
                    new Claim(ClaimTypes.Name, UtilizadorInfo.Nome.ToString())
                }),
                Expires = DateTime.UtcNow.AddHours(40),
                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256Signature)
            };

            var token = tokenHandler.CreateToken(tokenDescriptor);

            return tokenHandler.WriteToken(token);
        }



        /// <summary>
        /// Método que verifica se o Utilizador está inserido na API
        /// </summary>
        /// <param name="email">email que o Utilizador que introduzido</param>
        /// <param name="password">password que o Utilizador introduzio</param>
        /// <returns>Retorna o Utilizador caso ele exista</returns>
        /// <returns>NotFound caso o utilizador não exista </returns>
        private Utilizador UserExist(string email, string password)
        {

            List<Utilizador> Users = _context.Utilizador.ToList();

            Utilizador user = new Utilizador();

            foreach (Utilizador c in Users)
            {
                if (c.Email.Equals(email))
                {
                    user = c;
                }

            }

            if (user == null)
            {
                NotFound("Este Utilizador não existe!!!");
            }
            return user;

        }

        /// <summary>
        /// Este metodo vai possibilitar alterar a password 
        /// </summary>
        /// <param name="Id">Id do Utilizador</param>
        /// <param name="passwordDTO">ModelDTO da UpdatePassword</param>
        /// <returns>Mensagem de confirmação de que a password foi alterada</returns>
        //POST: api/updatePassword/id
        [HttpPost("updatePassword/{id}")]
        //[Authorize(Roles ="Funcionario, Cliente")]
        public async Task<IActionResult> updatePassword(int Id, PasswordDTO passwordDTO)
        {

            Utilizador user = await _context.Utilizador.FindAsync(Id);


            string oldPassword = user.Password;

            if (user != null)
            {
                if (BCrypt.Net.BCrypt.Verify(passwordDTO.OldPassword, oldPassword))
                {
                    if (passwordDTO.Password == passwordDTO.Confirm_Password)
                    {
                        string password = BCrypt.Net.BCrypt.HashPassword(passwordDTO.Password, 12);
                        user.Password = password;

                        await _context.SaveChangesAsync();
                        return Ok("Password Alterada.");
                    }
                    else
                    {
                        return BadRequest("As Password e Confirm_Password não são iguais.");
                    }
                }
                else
                {
                    return NotFound("Esta password não era a sua antiga password.");
                }

            }
            else
            {
                return NotFound("Este Utilizador não existe.");
            }
        }


        /// <summary>
        /// Este metodo vai permitir aceitar Utilizadores, tanto funcionários como Clientes
        /// </summary>
        /// <param name="id">id do Utilizador que queremos aceitar</param>
        /// <returns>NotFound quando o utilizador não existir</returns>
        /// <returns>BadRequest caso o utilizador já esteje aceite</returns>
        /// <returns>Ok caso o Utilizador seje aceite</returns>
        [HttpPut("aceitarUtilizador/{id}")]
        public async Task<IActionResult> AceitarUtilizador(int id)
        {

            Utilizador utilizador = await _context.Utilizador.FindAsync(id);


            if (utilizador == null)
            {
                return NotFound("Este Utilizador não existe!!!");
            }
            else if (utilizador.Aceite == true)
            {
                return BadRequest("Este Utilizador já se encontra Aceite!!!");
            }
            else
            {
                utilizador.Aceite = true;
                try
                {
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException) when (!UserExist(id))
                {
                    return NotFound();
                }
            }

            return Ok("Utilizador esta Aceite!!!");
        }

        /// <summary>
        /// Este metodo vai permitir alterar o contacto do Utilizador
        /// </summary>
        /// <param name="id">id do Utilizador que queremos mudar o contacto</param>
        /// <returns>NotFound quando o utilizador não existir</returns>
        /// <returns>BadRequest caso o utilizador já esteje aceite</returns>
        /// <returns>Ok caso o Utilizador seje aceite</returns>
        [HttpPut("alterarContacto/{id}/{contacto}")]
        public async Task<IActionResult> alterarDadosContacto(int id, int contacto)
        {

            Utilizador utilizador = await _context.Utilizador.FindAsync(id);

            if (utilizador == null)
            {
                return NotFound("Este Utilizador não existe!!!");
            }

            utilizador.Contacto = contacto;

            try
            {
                await _context.SaveChangesAsync();
            }

            catch (DbUpdateConcurrencyException) when (!UserExist(id))
            {
                return NotFound();
            }

            

            return Ok("Contacto Alterado!!!");
        }

       

        /// <summary>
        /// Este metodo vai permitir aceitar Utilizadores, tanto funcionários como Clientes
        /// </summary>
        /// <param name="id">id do Utilizador que queremos aceitar</param>
        /// <returns>NotFound quando o utilizador não existir</returns>
        /// <returns>BadRequest caso o utilizador já esteje aceite</returns>
        /// <returns>Ok caso o Utilizador seje aceite</returns>
        [HttpPut("alterarMorada/{id}")]
        public async Task<IActionResult> alterarDadosContacto(int id, MoradaDTO moradaDTO)
        {

            Utilizador utilizador = await _context.Utilizador.FindAsync(id);

            if (utilizador == null)
            {
                return NotFound("Este Utilizador não existe!!!");
            }

            utilizador.Morada = new Morada {
                Numero = moradaDTO.Numero,
                Rua = moradaDTO.Rua,
                CodigoPostal = moradaDTO.CodigoPostal,
                Concelho = moradaDTO.Concelho,
                Distrito = moradaDTO.Distrito,
                Freguesia = moradaDTO.Freguesia
            };

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!UserExist(id))
            {
                return NotFound();
            }
            return Ok("Morada Alterado!!!");
        }

        /// <summary>
        /// Metodo para quando esquecer a password poder a alterar
        /// </summary>
        /// <param name="id">Id do Utilizador a alterar a password</param>
        /// <param name="passwordDTO">O Dto do ForgetPassword</param>
        /// <returns>NotFound caso o Utilizador não exista</returns>
        /// <returns>BadRequest caso o email não coencida</returns>
        /// <returns>BadRequest caso a nova password e a da confirmação não sejam iguais</returns>
        /// <returns>Ok caso a password seja alterada com sucesso</returns>
        [HttpPost("forgetPassword/{email}")]
        //[Authorize(Roles ="Funcionario, Cliente")]
        public async Task<IActionResult> ForgetPassword(string email, ForgetPassword passwordDTO)
        {

            Utilizador user = await FindEmail(email);

            if (user == null)
            {
                return NotFound("Este Utilizador não existe!!");
            }

            if (user.Email != passwordDTO.Email)
            {
                return BadRequest("Este email não coencide com emails já criados");
            }

            if (passwordDTO.NewPassword != passwordDTO.Confirm_Password)
            {
                return BadRequest("A nova passwrod e a confirm Password têm de ser iguais.");
            }

            string password = BCrypt.Net.BCrypt.HashPassword(passwordDTO.NewPassword, 12);

            user.Password = password;


            await _context.SaveChangesAsync();
            return Ok("Password Alterada.");

        }

        /// <summary>
        /// Metodo que verifica se o email de facto existe ou não
        /// </summary>
        /// <param name="email">Email que desejamos procurar</param>
        /// <returns></returns>
        private async Task<Utilizador> FindEmail(string email)
        {
            List<Utilizador> listaDeUtilizadores = _context.Utilizador.ToList();

            Utilizador aux = null;

            foreach(var c in listaDeUtilizadores)
            {
                if (c.Email == email)
                {
                    aux = c;
                }
            }
            return aux;
        }


        
        /// <summary>
        /// Método para retornar os reports de um especifico Utilizador
        /// </summary>
        /// <returns>NotFound caso o Utilizador não exista na Base de Dados</returns>
        /// <returns>A lista de reports dependendo do tipo de Utilizador 
        /// sendo ele um Funcionario vão ser retornados os reports do tipo Cliente
        /// </returns>
        /// <returns>A lista de reports dependendo do tipo de Utilizador 
        /// sendo ele um Cliente vão ser retornados os reports do tipo Funcionário
        /// </returns>
        // GET: api/Reports/specificUser
        [HttpGet("Reports/{id}")]
        //[Authorize("Administrador")]
        public async Task<ActionResult<List<ReportsDetailsDTO>>> GetReportsSpecificUser(int id)
        {
            Utilizador user = await _context.Utilizador.FindAsync(id);

            List<ReportsDetailsDTO> listaDeReports = new List<ReportsDetailsDTO>();

            if (user == null)
            {
                return NotFound("Este Utilizador não existe.");
            }
            else
            {
                if (user.Cargo == Cargos.Funcionario)
                {
                    Funcionario funcionario = (Funcionario)user;

                    foreach (var report in funcionario.ListaDeReports)
                    {
                        listaDeReports.Add(new ReportsDetailsDTO
                        {
                            Titulo = report.Titulo,
                            Tipo = report.Tipo.ToString(),
                            Descricao = report.Descricao,
                            Cliente = report.Reporter.Nome,
                            Funcionario = report.Reporter.Nome
                        });
                    }


                }
                else if (user.Cargo == Cargos.Cliente)
                {
                    Cliente cliente = (Cliente)user;

                    foreach (var report in cliente.ListaDeReports)
                    {
                        listaDeReports.Add(new ReportsDetailsDTO
                        {
                            Tipo = report.Tipo.ToString(),
                            Titulo = report.Titulo,
                            Descricao = report.Descricao,
                            Cliente = report.Reported.Nome,
                            Funcionario = report.Reporter.Nome
                        });
                    }
                }
            }

            return listaDeReports;
        }


        /// <summary>
        /// Este método vai retornar a lista de Chats de determinado Utilizador
        /// </summary>
        /// <param name="id">Id do utilizador que queremos obter a lista de Chats</param>
        /// <returns>NotFound caso não exista o Utilizador </returns>
        /// <returns>Retorna a lista de chat do Utilizador</returns>
        [HttpGet("ChatList/{id}")]
        public async Task<ActionResult<IEnumerable<ChatDetailsDTO>>> GetChatList(int id)
        {
            var utilizador = await _context.Utilizador.FindAsync(id);

            if (utilizador == null)
            {
                return NotFound("O Utilizador não existe!!!");
            }

            List<ChatDetailsDTO> listaDeChats = new List<ChatDetailsDTO>();

            List<int> listChat = new List<int>();

            /// Guarda os ID dos chats do utilizador;
            foreach(var chat in utilizador.ListaDeChats)
            {
                listChat.Add(chat.Id);
            }


            var chatTemp = new Chat();

           
            /// Percorre a lista de Ids do Chat
            foreach(var n in listChat)
            {
                ///Procura o Chat na Base de Dados
                var chat = await _context.Chats.FindAsync(n);
                List<ChatMensagem> mensagems = new List<ChatMensagem>();

                ///Com o Chat que retornou na linha em cima vamos buscar as mensagens
                foreach (var f in chat.ListaMensagens)
                {
                    mensagems.Add(new ChatMensagem(f.Sender.Nome, f.Text));
                   
                }

                listaDeChats.Add(new ChatDetailsDTO
                {
                    id = chat.Id,
                    Cliente = chat.Cliente.Nome,
                    idFuncionario = chat.Funcionario.Id,
                    idCliente = chat.Cliente.Id,
                    imageCliente = chat.Cliente.Imagem,
                    Funcionario = chat.Funcionario.Nome,
                    imageFuncionario = chat.Funcionario.Imagem,
                    Mensagens = mensagems
                });

            }

            return listaDeChats;

        }


        /// <summary>
        /// Este metodo vai retornar uma lista de marcações de um Utilizador que estejam terminadas
        /// </summary>
        /// <param name="id">Id de um Utilizador</param>
        /// <returns>NotFound caso o Utilizador não exista na base de dados</returns>
        /// <returns>Lista de Marcações que já estão terminadas.</returns>
        /// <returns>NotFound caso o Utilizador não exista</returns>
        [HttpGet("ListMarcacoesTerminadas/{id}")]
        public async Task<ActionResult<IEnumerable<MarcacaoDTO>>> GetMarcacoesListTerminadas(int id)
        {
            var utilizador = await _context.Utilizador.FindAsync(id);

            if (utilizador == null)
            {
                return NotFound("O Utilizador não existe!!!");
            }

            List<MarcacaoDTO> list = new List<MarcacaoDTO>();

            foreach(var c in utilizador.ListaDeMarcacoes)
            {
                if(c.Terminada == true)
                {
                    MarcacaoDTO dto = new MarcacaoDTO
                    {
                        MarcacaoId=c.MarcacaoId,
                        TipoImovel = c.TipoImovel.ToString(),
                        TipoLimpeza = c.TipoLimpeza.ToString(),
                        TipoAgendamento = c.TipoAgendamento.ToString(),
                        NumQuartos = c.NumQuartos,
                        NumCasasDeBanho = c.NumCasasDeBanho,
                        Cozinha = c.Cozinha.ToString(),
                        Sala = c.Sala.ToString(),
                        Detalhes = c.Detalhes,
                        DiaHora = c.DiaHora,
                        HoraInicio = c.HoraInicial,
                        HoraFinal = c.HoraFinal,
                        Cliente = c.Cliente.Nome,
                        ClienteID = c.Cliente.Id,
                        Funcionario = c.Funcionario.Nome,
                        FuncionarioID = c.Funcionario.Id,
                        MarcacaoAceitePeloCliente = c.MarcacaoAceitePeloCliente.ToString(),
                        MarcacaoAceitePeloFunc = c.MarcacaoAceitePeloFunc.ToString(),
                        Terminada = c.Terminada.ToString(),
                        MoradaMarcacao = c.MoradaMarcacao,
                        LatitudeMarcacao = c.LatitudeMarcacao,
                        LongitudeMarcacao = c.LongitudeMarcacao,
                        Duracao = c.DuracaoTotal,
                        Total = c.Total,
                        NumHorasPrevistas = c.NumHorasPrevistas,
                        ReviewCliente = c.CliReview,
                        ReviewFuncionario = c.FuncReview
                    };

                    list.Add(dto);

                }
            }

            if (list.Count == 0)
            {
                return BadRequest("Não contem marcações terminadas!!!");
            }

            list = list.OrderBy(c => c.DiaHora).ToList();
            return list;

        }


        /// <summary>
        /// Este metodo vai retornar uma lista de marcações de um Utilizador que estejam terminadas
        /// </summary>
        /// <param name="id">Id de um Utilizador</param>
        /// <returns>NotFound caso o Utilizador não exista na base de dados</returns>
        /// <returns>Lista de Marcações</returns>
        [HttpGet("ListMarcacoesADecorrer/{id}")]
        public async Task<ActionResult<IEnumerable<MarcacaoDTO>>> GetMarcacoesListADecorrer(int id)
        {
            Utilizador utilizador = await _context.Utilizador.FindAsync(id);

            if (utilizador == null)
            {
                return NotFound("O Utilizador não existe!!!");
            }

            List<MarcacaoDTO> list = new List<MarcacaoDTO>();

            foreach (var c in utilizador.ListaDeMarcacoes)
            {
                if (c.Terminada == false && c.MarcacaoAceitePeloFunc == true)
                {
                    MarcacaoDTO dto = new MarcacaoDTO
                    {
                        MarcacaoId = c.MarcacaoId,
                        TipoImovel = c.TipoImovel.ToString(),
                        TipoLimpeza = c.TipoLimpeza.ToString(),
                        TipoAgendamento = c.TipoAgendamento.ToString(),
                        NumQuartos = c.NumQuartos,
                        NumCasasDeBanho = c.NumCasasDeBanho,
                        Cozinha = c.Cozinha.ToString(),
                        Sala = c.Sala.ToString(),
                        Detalhes = c.Detalhes,
                        DiaHora = c.DiaHora,
                        HoraInicio = c.HoraInicial,
                        HoraFinal = c.HoraFinal,
                        Cliente = c.Cliente.Nome,
                        ClienteID = c.Cliente.Id,
                        Funcionario = c.Funcionario.Nome,
                        FuncionarioID = c.Funcionario.Id,
                        MarcacaoAceitePeloCliente = c.MarcacaoAceitePeloCliente.ToString(),
                        MarcacaoAceitePeloFunc = c.MarcacaoAceitePeloFunc.ToString(),
                        Terminada = c.Terminada.ToString(),
                        MoradaMarcacao = c.MoradaMarcacao,
                        LatitudeMarcacao = c.LatitudeMarcacao,
                        LongitudeMarcacao = c.LongitudeMarcacao,
                        Duracao = c.DuracaoTotal,
                        Total = c.Total,
                        NumHorasPrevistas = c.NumHorasPrevistas,
                        ReviewCliente = c.CliReview,
                        ReviewFuncionario = c.FuncReview,
                        EstadoFuncionario = c.Funcionario.Estado.ToString()
                    };

                    list.Add(dto);

                }
            }

            if (list.Count == 0)
            {
                return BadRequest("Não contem marcações a decorrer!!!");
            }

            list = list.OrderBy(c => c.DiaHora).ToList();

            return list;

        }


        /// <summary>
        /// Este metodo vai retornar uma lista de marcações de um Utilizador que estejam terminadas
        /// </summary>
        /// <param name="id">Id de um Utilizador</param>
        /// <returns>NotFound caso o Utilizador não exista na base de dados</returns>
        /// <returns>Lista de Marcações</returns>
        [HttpGet("ListMarcacoesAindaNaoAceites/{id}")]
        public async Task<ActionResult<IEnumerable<MarcacaoDTO>>> GetMarcacoesListAindaNaoAceites(int id)
        {
            Utilizador utilizador = await _context.Utilizador.FindAsync(id);

            if (utilizador == null)
            {
                return NotFound("O Utilizador não existe!!!");
            }

            List<MarcacaoDTO> list = new List<MarcacaoDTO>();

            foreach (var c in utilizador.ListaDeMarcacoes)
            {
                if (c.MarcacaoAceitePeloFunc == false)
                {
                    MarcacaoDTO dto = new MarcacaoDTO
                    {
                        MarcacaoId = c.MarcacaoId,
                        TipoImovel = c.TipoImovel.ToString(),
                        TipoLimpeza = c.TipoLimpeza.ToString(),
                        TipoAgendamento = c.TipoAgendamento.ToString(),
                        NumQuartos = c.NumQuartos,
                        NumCasasDeBanho = c.NumCasasDeBanho,
                        Cozinha = c.Cozinha.ToString(),
                        Sala = c.Sala.ToString(),
                        Detalhes = c.Detalhes,
                        DiaHora = c.DiaHora,
                        HoraInicio = c.HoraInicial,
                        HoraFinal = c.HoraFinal,
                        Cliente = c.Cliente.Nome,
                        ClienteID = c.Cliente.Id,
                        Funcionario = c.Funcionario.Nome,
                        FuncionarioID = c.Funcionario.Id,
                        MarcacaoAceitePeloCliente = c.MarcacaoAceitePeloCliente.ToString(),
                        MarcacaoAceitePeloFunc = c.MarcacaoAceitePeloFunc.ToString(),
                        Terminada = c.Terminada.ToString(),
                        MoradaMarcacao = c.MoradaMarcacao,
                        LatitudeMarcacao = c.LatitudeMarcacao,
                        LongitudeMarcacao = c.LongitudeMarcacao,
                        Duracao = c.DuracaoTotal,
                        Total = c.Total,
                        NumHorasPrevistas = c.NumHorasPrevistas,
                        ReviewCliente = c.CliReview,
                        ReviewFuncionario = c.FuncReview
                    };

                    list.Add(dto);

                }
            }

            if (list.Count == 0)
            {
                return BadRequest("Não contem marcações a decorrer!!!");
            }

            list = list.OrderBy(c => c.DiaHora).ToList();

            return list;

        }





        /// <summary>
        /// Metodo para banir utilizadores
        /// </summary>
        /// <param name="id"></param>
        /// <returns>NotFound caso o utilizador não exista</returns>
        /// <returns>BadRequest caso o utilizador ja esteja banido</returns>
        /// <returns>Ok caso o utilizador seja banido, ou seja, a varialvel de banido esteja em true</returns>
        [HttpPut("banirUtilizador/{id}")]
        public async Task<IActionResult> BanirUtilizador(int id)
        {

            Utilizador utilizador = await _context.Utilizador.FindAsync(id);


            if (utilizador == null)
            {
                return NotFound("Este Utilizador não existe!!!");
            }
            else if (utilizador.Banido == true)
            {
                return BadRequest("Este utilizador já se encontra Banido!!!");
            }
            else
            {
                utilizador.Banido = true;
                try
                {
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException) when (!UserExist(id))
                {
                    return NotFound();
                }
            }

            return Ok("Utilizador esta Banido!!!");
        }


        /// <summary>
        /// Este método que permite desbanir um utilizador.
        /// </summary>
        /// <param name="id">id do Utilizador que deseja desbanir</param>
        /// <returns>NotFound caso o Utilizador foi desbanido</returns>
        /// <returns>NotFound caso o Utilizador não exista</returns>
        /// <returns>BadRequest caso o Utilizador esteja Banido</returns>
        /// <returns>Ok caso o Utilizador seja desebanido</returns>
        ///
        //PUT: desbanirUtilizador/id
        [HttpPut("desbanirUtilizador/{id}")]
        public async Task<IActionResult> DesbanirUtilizador(int id)
        {

            Utilizador utilizador = await _context.Utilizador.FindAsync(id);


            if (utilizador == null)
            {
                return NotFound("Este Utilizador não existe");
            }
            else if (utilizador.Banido == false)
            {
                return BadRequest("Este utilizador já se encontra Banido!!");
            }
            else
            {
                utilizador.Banido = false;
                try
                {
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException) when (!UserExist(id))
                {
                    return NotFound();
                }
            }

            return Ok("Utilizador desbanido");
        }


        /// <summary>
        /// Este metodo vai alterar a Latitude e Longitude
        /// </summary>
        /// <param name="id">ID do Utilizador a mudar a latitude e Longitude</param>
        /// <param name="latitudeLongitude">Latitude e Longitude do Utilizador </param>
        /// <returns>NotFound caso o UTilizador não exista</returns>
        /// <returns>Ok caso a Latitude e Longitude seja alterada com sucesso.</returns>

        [HttpPut("Latitude_Longitude/{id}")]
        public async Task<IActionResult> LatitudeLongitudeUtilizador(int id,LatitudeLongitudeDTO latitudeLongitude)
        {
            Utilizador utilizador = await _context.Utilizador.FindAsync(id);

            if(utilizador == null)
            {
                return NotFound("Este Utilizador não existe.");
            }

            utilizador.Latitude = latitudeLongitude.Latitude;
            utilizador.Longitude = latitudeLongitude.Longitude;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!UserExist(id))
            {
                return NotFound();
            }

            return Ok(utilizador);
        }


        /// <summary>
        /// Este metodo vai retornar todos utilizadores não aceites pelo o administrador
        /// </summary>
        /// <returns>A lista de Utilizadores não aceites pelo administrador</returns>
        [HttpGet("UtilizadoresNaoAceites")]
        public async Task<List<UtilizadorDetails>> ListaDeUtilizadoresNaoAceites()
        {
            var listaDeUtilizadores = await _context.Utilizador.ToListAsync();

            var listaDeUtilizadoresNaoAceites = new List<UtilizadorDetails>();


            foreach (var c in listaDeUtilizadores)
            {
                if (c.Aceite == false)
                {
                    var image = imageToByteArray(c.Imagem, c.Email);
                    UtilizadorDetails utilizador = new UtilizadorDetails
                    {
                        Id = c.Id,
                        Nome = c.Nome,
                        Imagem = image,
                        Morada = c.Morada,
                        Email = c.Email,
                        Idade = c.Idade,
                        TipoUtilizador = c.Cargo.ToString()
                    };
                    listaDeUtilizadoresNaoAceites.Add(utilizador);
                }
            }

            return listaDeUtilizadoresNaoAceites;
        }

        /// <summary>
        /// Este metodo vai retornar todos utilizadores não aceites pelo o administrador
        /// </summary>
        /// <returns>A lista de Utilizadores não aceites pelo administrador</returns>
        [HttpGet("UtilizadoresAceites")]
        public async Task<List<UtilizadorDetails>> ListaDeUtilizadoresAceites()
        {
            var listaDeUtilizadores = await _context.Utilizador.ToListAsync();

            var listaDeUtilizadoresAceites = new List<UtilizadorDetails>();


            foreach (var c in listaDeUtilizadores)
            {
                
                if (c.Aceite == true)
                {
                    var image = imageToByteArray(c.Imagem,c.Email);

                    UtilizadorDetails utilizador = new UtilizadorDetails
                    {
                        Id = c.Id,
                        Nome = c.Nome,
                        Imagem = image,
                        Morada = c.Morada,
                        Email = c.Email,
                        Idade = c.Idade,
                        TipoUtilizador = c.Cargo.ToString()
                    };
                    listaDeUtilizadoresAceites.Add(utilizador);
                }
            }

            return listaDeUtilizadoresAceites;
        }

        /// <summary>
        /// Este metodo vai retornar todos utilizadores banidos
        /// </summary>
        /// <returns>A lista de Utilizadores banidos pelo o administrador</returns>
        [HttpGet("TodosUtilizadoresBanidos")]
        public async Task<List<UtilizadorDetails>> ListaDeUtilizadoresBanidos()
        {
            var listaDeUtilizadores = await _context.Utilizador.ToListAsync();

            var listaDeUtilizadoresAceites = new List<UtilizadorDetails>();


            foreach (var c in listaDeUtilizadores)
            {

                if (c.Banido == true)
                {
                    var image = imageToByteArray(c.Imagem, c.Email);

                    UtilizadorDetails utilizador = new UtilizadorDetails
                    {
                        Id = c.Id,
                        Nome = c.Nome,
                        Imagem = image,
                        Morada = c.Morada,
                        Email = c.Email,
                        Idade = c.Idade,
                        TipoUtilizador = c.Cargo.ToString()
                    };
                    listaDeUtilizadoresAceites.Add(utilizador);
                }
            }

            return listaDeUtilizadoresAceites;
        }

        /// <summary>
        /// Este método vai retornar um Cliente especifico
        /// </summary>
        /// <param name="id">Id do Cliente que desejamos obter</param>
        /// <returns>NotFound caso o Cliente não exista</returns>
        /// <returns>Ok caso o Cliente exista</returns>
        // GET: api/Clientes/5
        [HttpGet("DetailsClientes/{id}")]
        //[Authorize(Roles ="Cliente, Funcionario")]
        public async Task<ActionResult<ClienteDetails>> GetCliente(int id)
        {
            Cliente cliente = await _context.Clientes.FindAsync(id);

            if (cliente == null)
            {
                return NotFound("O Cliente não existe na Base de Dados");
            }

            double mediaReviews = GetMediaReviewsClientes(cliente); 

            var imagem = imageToByteArray(cliente.Imagem, cliente.Email);
            
            ClienteDetails clienteDetails = new ClienteDetails
            {
                Nome = cliente.Nome,
                Idade = cliente.Idade,
                Id = cliente.Id,
                Contacto = cliente.Contacto,
                Morada = cliente.Morada,
                Email = cliente.Email,
                CcFile = cliente.CcFile,
                Cadastro = cliente.Cadastro,
                Imagem = imagem,
                TipoUtilizador = cliente.Cargo.ToString(),
                MediaReviews = mediaReviews
            };

            return clienteDetails;
        }


        /// <summary>
        /// Este método vai retornar um Cliente especifico
        /// </summary>
        /// <param name="id">Id do Cliente que desejamos obter</param>
        /// <returns>NotFound caso o Cliente não exista</returns>
        /// <returns>Ok caso o Cliente exista</returns>
        // GET: api/Clientes/5
        [HttpGet("DetailsClientesApp/{id}")]
        //[Authorize(Roles ="Cliente, Funcionario")]
        public async Task<ActionResult<ClienteDetails>> GetClienteApp(int id)
        {
            Cliente cliente = await _context.Clientes.FindAsync(id);

            if (cliente == null)
            {
                return NotFound("O Cliente não existe na Base de Dados");
            }

            double mediaReviews = GetMediaReviewsClientes(cliente);

            ClienteDetails clienteDetails = new ClienteDetails
            {
                Nome = cliente.Nome,
                Idade = cliente.Idade,
                Id = cliente.Id,
                Contacto = cliente.Contacto,
                Morada = cliente.Morada,
                Email = cliente.Email,
                CcFile = cliente.CcFile,
                Cadastro = cliente.Cadastro,
                TipoUtilizador = cliente.Cargo.ToString(),
                MediaReviews = mediaReviews
            };

            return clienteDetails;
        }


        /// <summary>
        /// Metodo para calcular a media das reviews do Funcionário
        /// </summary>
        /// <param name="funcionario">Funcionário que queremos calcular a media das reviews</param>
        /// <returns></returns>
        public double GetMediaReviewsFuncionarios(Funcionario funcionario)
        {
            int count = 0;
            double media = 0;

            foreach (var r in funcionario.ListaDeReviews)
            {
                media = media + r.Cotacao;
                count++;
            }

            double temp = Math.Round(media / count);
            return temp;
        }


        /// <summary>
        /// Metodo que retorna a média das reviews de um Cliente
        /// </summary>
        /// <param name="cliente">Cliente que queremos calcular</param>
        /// <returns></returns>
        public double GetMediaReviewsClientes(Cliente cliente)
        {
            int count = 0;
            double media = 0;

            foreach (var r in cliente.ListaDeReviews)
            {
                media = media + r.Cotacao;
                count++;
            }

            double temp = Math.Round(media / count);
            return temp;
        }

        /// <summary>
        /// Este método vai retornar um Cliente especifico
        /// </summary>
        /// <param name="id">Id do Cliente que desejamos obter</param>
        /// <returns>NotFound caso o Cliente não exista</returns>
        /// <returns>Ok caso o Cliente exista</returns>
        // GET: api/Clientes/5
        [HttpGet("DetailsFuncionarios/{id}")]
        //[Authorize(Roles ="Cliente, Funcionario")]
        public async Task<ActionResult<FuncionarioDetails>> GetFuncionario(int id)
        {
            Funcionario funcionario = await _context.Funcionarios.FindAsync(id);

            if (funcionario == null)
            {
                return NotFound("O Funcionário não existe na Base de Dados");
            }
            var imagem = imageToByteArray(funcionario.Imagem, funcionario.Email);

            double mediaDasReviews = GetMediaReviewsFuncionarios(funcionario);

            FuncionarioDetails funcionarioDetails = new FuncionarioDetails
            {
                Nome = funcionario.Nome,
                Idade = funcionario.Idade,
                Id = funcionario.Id,
                Morada = funcionario.Morada,
                Email = funcionario.Email,
                Contacto = funcionario.Contacto,
                CcFile = funcionario.CcFile,
                Cadastro = funcionario.Cadastro,
                Imagem = imagem,
                HistoricoMedico = funcionario.HistoricoMedico,
                CartaDeConducao = funcionario.CartaDeConducao,
                CvFile = funcionario.CvFile,
                TipoUtilizador = funcionario.Cargo.ToString(),
                Preco = funcionario.Preco,
                TipoLimpeza = funcionario.TipoLimpeza.ToString(),
                ValidadeSubscricao = funcionario.ValidadeSubscricao,
                EstadoFuncionario = funcionario.Estado.ToString(),
                MediaReviews = mediaDasReviews
            };

            return Ok(funcionarioDetails);
        }


        /// <summary>
        /// Este método vai retornar um Cliente especifico
        /// </summary>
        /// <param name="id">Id do Cliente que desejamos obter</param>
        /// <returns>NotFound caso o Cliente não exista</returns>
        /// <returns>Ok caso o Cliente exista</returns>
        // GET: api/Clientes/5
        [HttpGet("DetailsFuncionariosApp/{id}")]
        //[Authorize(Roles ="Cliente, Funcionario")]
        public async Task<ActionResult<FuncionarioDetails>> GetFuncionarioAPP(int id)
        {
            Funcionario funcionario = await _context.Funcionarios.FindAsync(id);

            if (funcionario == null)
            {
                return NotFound("O Funcionário não existe na Base de Dados");
            }
            
            double mediaDasReviews = GetMediaReviewsFuncionarios(funcionario);

            FuncionarioDetails funcionarioDetails = new FuncionarioDetails
            {
                Nome = funcionario.Nome,
                Idade = funcionario.Idade,
                Id = funcionario.Id,
                Morada = funcionario.Morada,
                Email = funcionario.Email,
                Contacto = funcionario.Contacto,
                CcFile = funcionario.CcFile,
                Cadastro = funcionario.Cadastro,
                HistoricoMedico = funcionario.HistoricoMedico,
                CartaDeConducao = funcionario.CartaDeConducao,
                CvFile = funcionario.CvFile,
                TipoUtilizador = funcionario.Cargo.ToString(),
                Preco = funcionario.Preco,
                TipoLimpeza = funcionario.TipoLimpeza.ToString(),
                ValidadeSubscricao = funcionario.ValidadeSubscricao,
                EstadoFuncionario = funcionario.Estado.ToString(),
                MediaReviews = mediaDasReviews
            };

            return Ok(funcionarioDetails);
        }

        /// <summary>
        /// Este método serve para converter uma imagem.
        /// </summary>
        /// <param name="imageName">imageName nome da imagem</param>
        /// <returns></returns>
        public byte[] imageToByteArray(string imageName, string userEmail)
        {
            var path = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot/Images",userEmail, imageName);
            Byte[] stream = System.IO.File.ReadAllBytes(path);
            return stream;
        }

        ///// <summary>
        ///// Este metodo vai realizar o download de Ficheiro do Utilizadores
        ///// </summary>
        ///// <param name="filename">o nome do Ficheiro</param>
        ///// <returns>Content, caso o file seja null</returns>
        [HttpGet("{id}/{filename}")]
        public async Task<IActionResult> DownloadFiles(int id, string filename)
        {

            var user = await _context.Utilizador.FindAsync(id);

            if(user == null)
            {
                return NotFound("Este Utilizador não existe!!!");
            }

            if (filename == null)
            {
                return NotFound("Este File não existe!!!");
            }

            var path = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot/Images",user.Email, filename);

            var memory = new MemoryStream();

            using (var stream = new FileStream(path, FileMode.Open))
            {
                await stream.CopyToAsync(memory);
            }

            memory.Position = 0;

            return File(memory, GetContentType(path), Path.GetFileName(path));
        }



        /// <summary>
        /// Este metodo vai possibilitar criar um Funcionario
        /// </summary>
        /// <param name="funcionarioDTO"></param>
        /// <returns>O Funcionario criado</returns>
        // POST: api/Funcionarios
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost("postAdmin")]
        [AllowAnonymous]
        public async Task<ActionResult<Utilizador>> PostAdmin()
        {
            Morada morada = new Morada
            {
                
                Rua = "",
                Concelho = "",
                CodigoPostal = "",
                Distrito = "",
                Freguesia = "",
                Numero =12
                
            };

            string password = BCrypt.Net.BCrypt.HashPassword("admin", 12);

            Utilizador user = new Utilizador
            {
                Nome="Admin",
                Password=password,
                Email="admin@gmail.com",
                Cargo = Cargos.Administrador,
                Contacto =112,
                Cadastro ="",
                Imagem = "",
                CcFile = "",
                Morada = morada
            };

            _context.Utilizador.Add(user);
            await _context.SaveChangesAsync();

            return user;

        }

        /// <summary>
        /// Metodo que retorna uma Imagem
        /// </summary>
        /// <param name="id">ID do Utilizador caso ele exista.</param>
        /// <returns>NotFound, caso o Utilizador não exista</returns>
        [HttpGet("GetImage/{id}")]
        public async Task<IActionResult> GetImage(int id)
        {
            var user = await _context.Utilizador.FindAsync(id);

            if (user == null)
            {
                return NotFound("Este Utilizador não existe.");
            }

            var path = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot/Images",user.Email, user.Imagem);
            Byte[] stream = System.IO.File.ReadAllBytes(path);

            return File(stream, GetContentType(path));
        }


        private string GetContentType(string path)
        {
            var types = GetMimeTypes();
            var ext = Path.GetExtension(path).ToLowerInvariant();
            return types[ext];
        }


        private Dictionary<string, string> GetMimeTypes()
        {
            return new Dictionary<string, string>
            {
                {".txt", "text/plain"},
                {".pdf", "application/pdf"},
                {".doc", "application/vnd.ms-word"},
                {".docx", "application/vnd.ms-word"},
                {".xls", "application/vnd.ms-excel"},
                {".xlsx", "application/vnd.openxmlformatsofficedocument.spreadsheetml.sheet"},
                {".png", "image/png"},
                {".jpg", "image/jpeg"},
                {".jpeg", "image/jpeg"},
                {".gif", "image/gif"},
                {".csv", "text/csv"}
            };
        }

        /// <summary>
        /// Método que verifica se de determinado Utilizador existe
        /// </summary>
        /// <param name="id">Id do Utilizador a pesquisar</param>
        /// <returns>Retorna o Utilizador caso o Utilizador exista</returns>
        private bool UserExist(int id)
        {
            return _context.Utilizador.Any(e => e.Id == id);
        }

        

    }
}
