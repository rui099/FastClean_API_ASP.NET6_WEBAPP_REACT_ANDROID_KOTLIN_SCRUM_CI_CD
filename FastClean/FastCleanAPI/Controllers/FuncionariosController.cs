using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using FastCleanAPI.Models;
using FastCleanAPI.DTO_s.Funcionario;
using Microsoft.AspNetCore.Authorization;
using FastCleanAPI.DTO_s.ReviewFuncionarioDTO;
using FastCleanAPI.DTO_s.Utilizador;

namespace FastCleanAPI.Controllers
{
    [Route("api/[Controller]")]
    [ApiController]
    public class FuncionariosController : Controller
    {
        private readonly IWebHostEnvironment _hostingEnv;
        private readonly FastCleanContext _context;

        /// <summary>
        /// Metodo Construtor
        /// </summary>
        /// <param name="context"></param>
        /// <param name="hostingEnv"></param>
        public FuncionariosController(FastCleanContext context, IWebHostEnvironment hostingEnv)
        {
            _context = context;
            _hostingEnv = hostingEnv;


        }

       
        
       


        /// <summary>
        /// Este metodo vai possibilitar criar um Funcionario
        /// </summary>
        /// <param name="funcionarioDTO"></param>
        /// <returns>O Funcionario criado</returns>
        // POST: api/Funcionarios
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [AllowAnonymous]
        public async Task<ActionResult<FuncionarioRegister>> PostFuncionarioRegisterDTO([FromForm] FuncionarioRegister funcionarioDTO)
        {
            string imagem = uploadImagem(funcionarioDTO);
            string cCFile = uploadCcFile(funcionarioDTO);
            string cadastro = uploadCadastro(funcionarioDTO);
            string historicoMedico = uploadHistoricoMedico(funcionarioDTO);
            string cartaDeConducao = uploadCartaDeConducao(funcionarioDTO);
            string cvFile = uploadCVFile(funcionarioDTO);


            if (!imagem.EndsWith(".PNG") && !imagem.EndsWith(".png") && !imagem.EndsWith(".jpg"))
            {
                return BadRequest("Só são aceiteis imagens no formato PNG e JPG");
            }

            if (!cCFile.EndsWith(".pdf"))
            {
                return BadRequest("O ficheiro do Cartão de Cidadão tem de ser enviado em pdf");
            }

            if (!cadastro.EndsWith(".pdf"))
            {
                return BadRequest("O ficheiro do cadastro tem de ser em pdf!!!");
            }

            if (!historicoMedico.EndsWith(".pdf"))
            {
                return BadRequest("O ficheiro do Histórico Médico tem de ser em pdf!!!");
            }

            if (!cartaDeConducao.EndsWith(".pdf"))
            {
                return BadRequest("O ficheiro do Carta de Condução tem de ser em pdf!!!");
            }

            if (!cvFile.EndsWith(".pdf"))
            {
                return BadRequest("O ficheiro do CV tem de ser em pdf!!!");
            }

            string password = BCrypt.Net.BCrypt.HashPassword(funcionarioDTO.Password,12);

            List<Utilizador> lista = await _context.Utilizador.ToListAsync();

            var today = DateTime.Today;

            int idade = today.Year - funcionarioDTO.Idade.Year;

            foreach (Utilizador c in lista)
            {
                if (c.Email == funcionarioDTO.Email)
                {
                    return BadRequest("Já existe um email repetido.");
                }
            }

            if(funcionarioDTO != null)
            {
                Funcionario funcionario = new Funcionario
                {
                    Nome = funcionarioDTO.Nome,
                    Password = password,
                    Cargo = Cargos.Funcionario,
                    Email = funcionarioDTO.Email,
                    TipoLimpeza = funcionarioDTO.TipoLimpeza,
                    Preco = funcionarioDTO.Preco,
                    Idade = idade,
                    Morada = funcionarioDTO.Morada,
                    Contacto = funcionarioDTO.Contacto,
                    CcFile = cCFile,
                    Imagem = imagem,
                    Cadastro = cadastro,
                    CartaDeConducao = cartaDeConducao,
                    HistoricoMedico = historicoMedico,
                    CvFile = cvFile,
                    Subscricao = false,
                    Banido = false,
                    Aceite = false,
                    Estado = EstadoFuncionario.Indisponivel
                };

                _context.Funcionarios.Add(funcionario);
                await _context.SaveChangesAsync();


                return Ok(funcionario);
            }

            return BadRequest("Erro a criar um Utilizador.");    
           
        }

        /// <summary>
        /// Este metodo vai mudar o estado do Funcionário para ocupado
        /// </summary>
        /// <param name="id">ID do Funcionário que queremos mudar o estado</param>
        /// <returns>NotFound, caso o Funcionário não exista</returns>
        /// <returns>BadRequest, caso o Funcionário já se encontre com o estado Ocupado</returns>
        [HttpPut("Ocupado/{id}")]
        public async Task<IActionResult> MudarEstadoOcupado(int id)
        {
            var funcionario = await _context.Funcionarios.FindAsync(id);

            if(funcionario == null)
            {
                return NotFound("Este Funcionário não existe!!!");
            }

            if((int) funcionario.Estado == 1)
            {
                return BadRequest("Já se encontra o seu estado em Ocupado!!!");
            }

            funcionario.Estado = EstadoFuncionario.Ocupado;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!FuncionarioExists(id))
            {
                return NotFound();
            }

            return NoContent();
        }


        /// <summary>
        /// Este metodo vai mudar o estado do Funcionário para Indisponivel
        /// </summary>
        /// <param name="id">ID do Funcionário que queremos mudar o estado</param>
        /// <returns>NotFound, caso o Funcionário não exista</returns>
        /// <returns>BadRequest, caso o Funcionário já se encontre com o estado Indisponível</returns>
        [HttpPut("Indisponivel/{id}")]
        public async Task<IActionResult> MudarEstadaoIndisponível(int id)
        {
            var funcionario = await _context.Funcionarios.FindAsync(id);

            if (funcionario == null)
            {
                return NotFound("Este Funcionário não existe!!!");
            }

            if ((int) funcionario.Estado == 0)
            {
                return BadRequest("Já se encontra o seu estado em Indiponível!!!");
            }

            funcionario.Estado = EstadoFuncionario.Indisponivel;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!FuncionarioExists(id))
            {
                return NotFound();
            }

            return NoContent();
        }

        /// <summary>
        /// Este metodo vai mudar o estado do Funcionário para Disponivel
        /// </summary>
        /// <param name="id">ID do Funcionário que queremos mudar o estado</param>
        /// <returns>NotFound, caso o Funcionário não exista</returns>
        /// <returns>BadRequest, caso o Funcionário já se encontre com o estado Disponivel</returns>
        [HttpPut("Disponivel/{id}")]
        public async Task<IActionResult> MudarEstadaoDisponivel(int id)
        {
            var funcionario = await _context.Funcionarios.FindAsync(id);

            if (funcionario == null)
            {
                return NotFound("Este Funcionário não existe!!!");
            }

            if ((int) funcionario.Estado == 2)
            {
                return BadRequest("Já se encontra o seu estado em Disponivel!!!");
            }

            funcionario.Estado = EstadoFuncionario.Disponível;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!FuncionarioExists(id))
            {
                return NotFound();
            }

            return Ok("Funcionário já se encontra Disponivel!!!");
        }


        /// <summary>
        /// Este metodo vai retornar o estado do Funcionário
        /// </summary>
        /// <returns>O estado do Funcionário</returns>
        [HttpGet("estadoFuncionario/{id}")]
        public async Task<ActionResult<string>> EstadoDoFuncionario(int id)
        {
            Funcionario funcionario = await _context.Funcionarios.FindAsync(id);

            if (funcionario == null)
            {
                return NotFound("Este Funcionário não existe!!!");
            }

            return funcionario.Estado.ToString(); ;
        }


        /// <summary>
        /// Metodo que permite alterar o CcFile do Funcionario
        /// </summary>
        /// <param name="id">Id Do Funcionário</param>
        /// <param name="file">Informação que vai ser alterada do Funcionario</param>
        /// <param name="email">Email do Funcionario que queremos mudar</param>
        /// <returns>Retorna o Funcionario com a CcFile alterada alterado</returns>
        // PUT: api/Clientes/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("AtualizarFuncionarioCcfile/{id}/{email}")]
        //[Authorize("Cliente")]
        public async Task<ActionResult> PutCcfileFuncionario([FromForm] ImageDTO file, int id, string email)
        {
            Funcionario funcionario = await _context.Funcionarios.FindAsync(id);
            string imagem = null;

            if(file.Imagem.FileName.EndsWith(".pdf"))
            {
                imagem = updateUploadFile(file, email);
                string filetemp = Path.Combine(_hostingEnv.WebRootPath, "Images/", email, funcionario.CcFile);
                System.IO.File.Delete(filetemp);
            }
            else
            {
                return BadRequest("Este ficheiro tem ser do tipo PDF!!!!");
            }

  
            if (funcionario== null)
            {
                return NotFound("Este Cliente não existe na Base de Dados.");
            }

            if (funcionario.Email != email)
            {
                return NotFound("Este email não corresponde ao email do Utilizador!!!");
            }

            funcionario.CcFile = imagem;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!FuncionarioExists(id))
            {
                return NotFound();
            }

            return Ok(funcionario);
        }


        /// <summary>
        /// Metodo que permite alterar o Imagem do Funcionario
        /// </summary>
        /// <param name="id">Id Do Funcionário</param>
        /// <param name="file">Informação que vai ser alterada do Funcionario</param>
        /// <param name="email">Email do Funcionario que queremos mudar</param>
        /// <returns>Retorna o Funcionario com a Imagem alterada alterado</returns>
        // PUT: api/Clientes/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("AtualizarFuncionarioImagem/{id}/{email}")]
        //[Authorize("Cliente")]
        public async Task<ActionResult> PutImagemFuncionario([FromForm] ImageDTO file, int id, string email)
        {
            Funcionario funcionario = await _context.Funcionarios.FindAsync(id);
            string imagem = null;

            if (file.Imagem.FileName.EndsWith(".png") || file.Imagem.FileName.EndsWith(".PNG") || file.Imagem.FileName.EndsWith("jpg"))
            {
                imagem = updateUploadFile(file, email);
                string filetemp = Path.Combine(_hostingEnv.WebRootPath, "Images/", email, funcionario.CcFile);
                System.IO.File.Delete(filetemp);
            }
            else
            {
                return BadRequest("Tem que ser ficheiros do tipo png e jpg!!");
            }



            if (funcionario == null)
            {
                return NotFound("Este Cliente não existe na Base de Dados.");
            }


            if (funcionario.Email != email)
            {
                return NotFound("Este email não corresponde ao email do Utilizador!!!");
            }


            funcionario.Imagem = imagem;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!FuncionarioExists(id))
            {
                return NotFound();
            }

            return Ok(funcionario);
        }


        /// <summary>
        /// Metodo que permite alterar o CvFile do Funcionario
        /// </summary>
        /// <param name="id">Id Do Funcionário</param>
        /// <param name="file">Informação que vai ser alterada do Funcionario</param>
        /// <param name="email">Email do Funcionario que queremos mudar</param>
        /// <returns>Retorna o Funcionario com a CvFile alterada alterado</returns>
        // PUT: api/Clientes/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("AtualizarFuncionarioCvfile/{id}/{email}")]
        //[Authorize("Cliente")]
        public async Task<ActionResult> PutCvfileFuncionario([FromForm] ImageDTO file, int id, string email)
        {
            Funcionario funcionario = await _context.Funcionarios.FindAsync(id);
            string imagem = null;

            if (file.Imagem.FileName.EndsWith(".pdf"))
            {
                imagem = updateUploadFile(file, email);
                string filetemp = Path.Combine(_hostingEnv.WebRootPath, "Images/", email, funcionario.CvFile);
                System.IO.File.Delete(filetemp);
            }
            else
            {
                return BadRequest("Este ficheiro tem ser do tipo PDF!!!!");
            }

            if (funcionario == null)
            {
                return NotFound("Este Cliente não existe na Base de Dados.");
            }

            if (funcionario.Email != email)
            {
                return NotFound("Este email não corresponde ao email do Utilizador!!!");
            }

            funcionario.CvFile = imagem;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!FuncionarioExists(id))
            {
                return NotFound();
            }

            return Ok(funcionario);
        }

        /// <summary>
        /// Metodo que permite alterar o Carta de Condução do Funcionario
        /// </summary>
        /// <param name="id">Id Do Funcionário</param>
        /// <param name="file">Informação que vai ser alterada do Funcionario</param>
        /// <param name="email">Email do Funcionario que queremos mudar</param>
        /// <returns>Retorna o Funcionario com a Carta de Condução alterada alterado</returns>
        // PUT: api/Clientes/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("AtualizarFuncionarioCartaDeConducao/{id}/{email}")]
        //[Authorize("Cliente")]
        public async Task<ActionResult> PutCartaDeConducaoFuncionario([FromForm] ImageDTO file, int id, string email)
        {
            Funcionario funcionario = await _context.Funcionarios.FindAsync(id);
            string imagem = null;

            if (file.Imagem.FileName.EndsWith(".pdf"))
            {
                imagem = updateUploadFile(file, email);
                string filetemp = Path.Combine(_hostingEnv.WebRootPath, "Images/", email, funcionario.CartaDeConducao);
                System.IO.File.Delete(filetemp);
            }
            else
            {
                return BadRequest("Este ficheiro tem ser do tipo PDF!!!!");
            }



            if (funcionario == null)
            {
                return NotFound("Este Funcionário não existe na Base de Dados.");
            }


            if (funcionario.Email != email)
            {
                return NotFound("Este email não corresponde ao email do Utilizador!!!");
            }


            funcionario.CartaDeConducao = imagem;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!FuncionarioExists(id))
            {
                return NotFound();
            }

            return Ok(funcionario);
        }

        /// <summary>
        /// Metodo que permite alterar o Carta de Condução do Funcionario
        /// </summary>
        /// <param name="id">Id Do Funcionário</param>
        /// <param name="file">Informação que vai ser alterada do Funcionario</param>
        /// <param name="email">Email do Funcionario que queremos mudar</param>
        /// <returns>Retorna o Funcionário com a Carta de Condução alterada alterado</returns>
        // PUT: api/Clientes/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("AtualizarFuncionarioHistoricoMedico/{id}/{email}")]
        //[Authorize("Cliente")]
        public async Task<ActionResult> PutHistoricoMedicoFuncionario([FromForm] ImageDTO file, int id, string email)
        {
            Funcionario funcionario = await _context.Funcionarios.FindAsync(id);
            string imagem = null;

            if (file.Imagem.FileName.EndsWith(".pdf"))
            {
                imagem = updateUploadFile(file, email);
                string filetemp = Path.Combine(_hostingEnv.WebRootPath, "Images/", email, funcionario.HistoricoMedico);
                System.IO.File.Delete(filetemp);
            }
            else
            {
                return BadRequest("Este ficheiro tem ser do tipo PDF!!!!");
            }

            if (funcionario == null)
            {
                return NotFound("Este Funcionário não existe na Base de Dados.");
            }


            if (funcionario.Email != email)
            {
                return NotFound("Este email não corresponde ao email do Utilizador!!!");
            }


            funcionario.HistoricoMedico = imagem;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!FuncionarioExists(id))
            {
                return NotFound();
            }

            return Ok(funcionario);
        }

        /// <summary>
        /// Metodo que permite alterar o Cadastro do Funcionario
        /// </summary>
        /// <param name="id">Id Do Funcionário</param>
        /// <param name="file">Informação que vai ser alterada do Funcionario</param>
        /// <param name="email">Email do Funcionario que queremos mudar</param>
        /// <returns>Retorna o Funcionário com a Cadastro alterada alterado</returns>
        // PUT: api/Clientes/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("AtualizarFuncionarioCadastro/{id}/{email}")]
        //[Authorize("Cliente")]
        public async Task<ActionResult> PutCadastroFuncionario([FromForm] ImageDTO file, int id, string email)
        {
            Funcionario funcionario = await _context.Funcionarios.FindAsync(id);
            string imagem = null;

            if (file.Imagem.FileName.EndsWith(".pdf"))
            {
                imagem = updateUploadFile(file, email);
                string filetemp = Path.Combine(_hostingEnv.WebRootPath, "Images/", email, funcionario.Cadastro);
                System.IO.File.Delete(filetemp);
            }
            else
            {
                return BadRequest("Este ficheiro tem ser do tipo PDF!!!!");
            }

            if (funcionario == null)
            {
                return NotFound("Este Funcionário não existe na Base de Dados.");
            }

            if (funcionario.Email != email)
            {
                return NotFound("Este email não corresponde ao email do Utilizador!!!");
            }

            funcionario.Cadastro = imagem;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!FuncionarioExists(id))
            {
                return NotFound();
            }

            return Ok(funcionario);
        }

        /// <summary>
        /// Este metodo vai permitir alterar o preço do Funcionário
        /// </summary>
        /// <param name="id">id do Utilizador que queremos mudar o contacto</param>
        /// <returns>NotFound quando o utilizador não existir</returns>
        /// <returns>BadRequest caso o utilizador já esteje aceite</returns>
        /// <returns>Ok caso o Utilizador seje aceite</returns>
        [HttpPut("alterarPreco/{id}/{preco}")]
        public async Task<IActionResult> alterarDadosContacto(int id, double preco)
        {

            Funcionario utilizador = await _context.Funcionarios.FindAsync(id);

            if (utilizador == null)
            {
                return NotFound("Este Funcionário não existe!!!");
            }

            utilizador.Preco = preco;

            try
            {
                await _context.SaveChangesAsync();
            }

            catch (DbUpdateConcurrencyException) when (!FuncionarioExists(id))
            {
                return NotFound();
            }



            return Ok("Preço Alterado!!!");
        }

        /// <summary>
        /// Metodo para atualizar Imagem do Cliente
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        private string updateUploadFile(ImageDTO model, string email)
        {
            string uniqueFileName = null;

            if (model.Imagem != null)
            {
                string uploadsFolder = Path.Combine(_hostingEnv.WebRootPath, "Images/", email);

                bool exists = System.IO.Directory.Exists(uploadsFolder);

                if (exists)
                {
                    uniqueFileName = Guid.NewGuid().ToString() + "_" + model.Imagem.FileName;

                    string filePath = Path.Combine(uploadsFolder, uniqueFileName);

                    using (var fileStream = new FileStream(filePath, FileMode.Create))
                    {
                        model.Imagem.CopyTo(fileStream);
                    }
                }
            }

            return uniqueFileName;
        }

        /// <summary>
        /// Este metodo vai criar a 
        /// </summary>
        /// <param name="id">ID do Funcionário que queremos mudar o estado</param>
        /// <returns>NotFound, caso o Funcionário não exista</returns>
        /// <returns>BadRequest, caso o Funcionário já se encontre com o estado Disponivel</returns>
        [HttpPut("Subscricao/{id}")]
        public async Task<IActionResult> PrimeiraSubscricao(int id)
        {
            var funcionario = await _context.Funcionarios.FindAsync(id);

            bool flag = false;
            int diferenca = 0;

            if (funcionario == null)
            {
                return NotFound("Este Funcionário não existe!!!");
            }
            DateTime dateTemp = DateTime.Now;
            if (funcionario.ValidadeSubscricao == null && funcionario.Subscricao == false)
            {
                flag = true;
                funcionario.ValidadeSubscricao = String.Format("{0:dd/MM/yyyy}", dateTemp); 
                funcionario.Subscricao = true;
            }
            else if(funcionario.ValidadeSubscricao != null && funcionario.Subscricao == false)
            {
                DateTime dateSubscricao = Convert.ToDateTime(funcionario.ValidadeSubscricao);

                TimeSpan dateCalculo = dateTemp - dateSubscricao;

                diferenca = dateCalculo.Days;

                if (diferenca > 30 && funcionario.Subscricao==false)
                {
                    flag = true;
                    funcionario.ValidadeSubscricao = String.Format("{0:dd/MM/yyyy}", dateTemp);
                    funcionario.Subscricao = true;
                }
            }

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!FuncionarioExists(id))
            {
                return NotFound();
            }

            if (flag == true)
            {
                return Ok(funcionario.ValidadeSubscricao.ToString());
            }
            else
            {
                return Ok("Ainda não se pode subscrever!!!");
            }
        }


        /// <summary>
        /// Este metodo vai anular a subscrição quaso a validade esteja terminada 
        /// </summary>
        /// <param name="id">ID do Funcionário que vai ser anulada a subscrição</param>
        [HttpPut("AnularSubscricao/{id}")]
        public async Task<IActionResult> AnularSubscricao(int id)
        {
            var funcionario = await _context.Funcionarios.FindAsync(id);

  
            if (funcionario == null)
            {
                return NotFound("Este Funcionário não existe!!!");
            }

            DateTime dateTemp = DateTime.Now;
            DateTime dateSubscricao = Convert.ToDateTime(funcionario.ValidadeSubscricao);

            TimeSpan dateCalculo = dateTemp - dateSubscricao;

           int diferenca = dateCalculo.Days;

           if(diferenca > 30)
            {
                funcionario.Subscricao = false;
            }

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!FuncionarioExists(id))
            {
                return NotFound();
            }

            return Ok(funcionario.Subscricao);
        }

        /// <summary>
        /// Metodo que retorna o estada da Subscrição do Funcionario
        /// </summary>
        /// <param name="id">Id do Funcionário que queremos ver a subscrição</param>
        /// <returns>True or False</returns>
        [HttpGet("VerificaSubscricao/{id}")]
        public async Task<ActionResult<bool>> GetValidadeSubscricao(int id)
        {

            Funcionario funcionario = await _context.Funcionarios.FindAsync(id);

            if (funcionario == null)
            {
                return NotFound("Este Funcionário não existe!!");
            }

            return funcionario.Subscricao;
        }


        /// <summary>
        /// Este metodo vai criar a 
        /// </summary>
        /// <param name="id">ID do Funcionário que queremos mudar o estado</param>
        /// <returns>NotFound, caso o Funcionário não exista</returns>
        /// <returns>BadRequest, caso o Funcionário já se encontre com o estado Disponivel</returns>
        [HttpPut("MudarValidade/{id}")]
        public async Task<IActionResult> MudarValidade(int id)
        {
            var funcionario = await _context.Funcionarios.FindAsync(id);

            if (funcionario == null)
            {
                return NotFound("Este Funcionário não existe!!!");
            }

            funcionario.ValidadeSubscricao = "30/12/2021";

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!FuncionarioExists(id))
            {
                return NotFound();
            }

            return Ok(funcionario.ValidadeSubscricao);
        }



        

        /// <summary>
        /// Este metodo vai fazer upload do Ficheiro do Cadastro ao registar o Funcionario
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        private string uploadCadastro(FuncionarioRegister model)
        {
            string uniqueFileName = null;

            if (model.Cadastro != null)
            {
                string uploadsFolder = Path.Combine(_hostingEnv.WebRootPath, "Images/", model.Email);

                System.IO.Directory.CreateDirectory(uploadsFolder);

                uniqueFileName = Guid.NewGuid().ToString() + "_" + model.Cadastro.FileName;

                string filePath = Path.Combine(uploadsFolder, uniqueFileName);

                using (var fileStream = new FileStream(filePath, FileMode.Create))
                {
                    model.Cadastro.CopyTo(fileStream);
                }
            }

            return uniqueFileName;
        }


        /// <summary>
        /// Metodo para fazer upload de Imagem do Funcionario
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        private string uploadImagem(FuncionarioRegister model)
        {
            string uniqueFileName = null;

            if (model.Imagem != null)
            {
                string uploadsFolder = Path.Combine(_hostingEnv.WebRootPath, "Images/", model.Email);

                System.IO.Directory.CreateDirectory(uploadsFolder);

                uniqueFileName = Guid.NewGuid().ToString() + "_" + model.Imagem.FileName;

                string filePath = Path.Combine(uploadsFolder, uniqueFileName);

                using (var fileStream = new FileStream(filePath, FileMode.Create))
                {
                    model.Imagem.CopyTo(fileStream);
                }
            }

            return uniqueFileName;
        }


        /// <summary>
        /// Metodo para fazer upload do CcFile do Funcionario
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        private string uploadCcFile(FuncionarioRegister model)
        {
            string uniqueFileName = null;

            if (model.Ccfile != null)
            {
                string uploadsFolder = Path.Combine(_hostingEnv.WebRootPath, "Images/", model.Email);

                System.IO.Directory.CreateDirectory(uploadsFolder);

                uniqueFileName = Guid.NewGuid().ToString() + "_" + model.Ccfile.FileName;

                string filePath = Path.Combine(uploadsFolder, uniqueFileName);

                using (var fileStream = new FileStream(filePath, FileMode.Create))
                {
                    model.Ccfile.CopyTo(fileStream);
                }
            }

            return uniqueFileName;
        }

        /// <summary>
        /// Metodo para fazer upload do file do CvFile do Funcionario
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        private string uploadCVFile(FuncionarioRegister model)
        {
            string uniqueFileName = null;

            if (model.CvFile != null)
            {
                string uploadsFolder = Path.Combine(_hostingEnv.WebRootPath, "Images/", model.Email);

                System.IO.Directory.CreateDirectory(uploadsFolder);

                uniqueFileName = Guid.NewGuid().ToString() + "_" + model.CvFile.FileName;

                string filePath = Path.Combine(uploadsFolder, uniqueFileName);

                using (var fileStream = new FileStream(filePath, FileMode.Create))
                {
                    model.CvFile.CopyTo(fileStream);
                }
            }

            return uniqueFileName;
        }
        /// <summary>
        /// Metodo para fazer upload de um file do Historico Medico do Funcionario
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        private string uploadHistoricoMedico(FuncionarioRegister model)
        {
            string uniqueFileName = null;

            if (model.HistoricoMedico != null)
            {
                string uploadsFolder = Path.Combine(_hostingEnv.WebRootPath, "Images/", model.Email);

                System.IO.Directory.CreateDirectory(uploadsFolder);

                uniqueFileName = Guid.NewGuid().ToString() + "_" + model.HistoricoMedico.FileName;

                string filePath = Path.Combine(uploadsFolder, uniqueFileName);

                using (var fileStream = new FileStream(filePath, FileMode.Create))
                {
                    model.HistoricoMedico.CopyTo(fileStream);
                }
            }

            return uniqueFileName;
        }

        /// <summary>
        /// Metodo para fazer upload da Carta de Condução do Funcionario
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        private string uploadCartaDeConducao(FuncionarioRegister model)
        {
            string uniqueFileName = null;

            if (model.CartaDeConducao != null)
            {
                string uploadsFolder = Path.Combine(_hostingEnv.WebRootPath, "Images/", model.Email);

                System.IO.Directory.CreateDirectory(uploadsFolder);

                uniqueFileName = Guid.NewGuid().ToString() + "_" + model.CartaDeConducao.FileName;

                string filePath = Path.Combine(uploadsFolder, uniqueFileName);

                using (var fileStream = new FileStream(filePath, FileMode.Create))
                {
                    model.CartaDeConducao.CopyTo(fileStream);
                }
            }

            return uniqueFileName;
        }

        /// <summary>
        /// Metodo para transformar uma imagem em array de bytes
        /// </summary>
        /// <param name="imageName">nome da imagem</param>
        /// <param name="userEmail">email do Utilizador</param>
        /// <returns></returns>
        public byte[] imageToByteArray(string imageName, string userEmail)
        {
            var path = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot/Images", userEmail, imageName);
            Byte[] stream = System.IO.File.ReadAllBytes(path);
            return stream;
        }

        /// <summary>
        /// Este método vai retornar um Cliente especifico
        /// </summary>
        /// <param name="id">Id do Cliente que desejamos obter</param>
        /// <returns>NotFound caso o Cliente não exista</returns>
        /// <returns>Ok caso o Cliente exista</returns>
        // GET: api/Clientes/5
        [HttpGet("{id}")]
        //[Authorize(Roles ="Cliente, Funcionario")]
        public async Task<ActionResult<FuncionarioDetails>> GetFuncionario(int id)
        {
            Funcionario funcionario = await _context.Funcionarios.FindAsync(id);

            if (funcionario == null)
            {
                return NotFound("O Funcionário não existe na Base de Dados");
            }

            
            var imagem = imageToByteArray(funcionario.Imagem, funcionario.Email);
           
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
                CvFile = funcionario.CvFile,
                TipoUtilizador = funcionario.Cargo.ToString(),
                Preco = funcionario.Preco,
                TipoLimpeza = funcionario.TipoLimpeza.ToString()
            };

            return Ok(funcionarioDetails);
        }


        /// <summary>
        /// Este método se retornar o funcionario esta subscrito ou não
        /// </summary>
        /// <param name="id">Id do Cliente que desejamos obter</param>
        /// <returns>NotFound caso o Cliente não exista</returns>
        /// <returns>True or False</returns>
        [HttpGet("Subscricao/{id}")]
        //[Authorize(Roles ="Cliente, Funcionario")]
        public async Task<ActionResult<bool>> GetSubscricao(int id)
        {
            Funcionario funcionario = await _context.Funcionarios.FindAsync(id);

            if (funcionario == null)
            {
                return NotFound("O Funcionário não existe na Base de Dados");
            }
            return funcionario.Subscricao;
        }



        /// <summary>
        /// Metodo para mostrar as reviews de um Funcionário
        /// </summary>
        /// <param name="id">Id do Funcionário</param>
        /// <returns></returns>

        [HttpGet("reviewsFuncionario/{id}")]
        ///[Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<IEnumerable<FuncionarioReviewDetails>>> getReviewsFuncionario(int id)
        {
            var funcionario = await _context.Funcionarios.FindAsync(id);

            if (funcionario == null)
            {
                return NotFound("Este Funcionário não existe!!");
            }


            List<FuncionarioReviewDetails> listaReviews = new List<FuncionarioReviewDetails>();


            foreach (var review in funcionario.ListaDeReviews)
            {
                listaReviews.Add(new FuncionarioReviewDetails
                {
                    Id = review.Id,
                    Comentario = review.Comentario,
                    Cotacao = review.Cotacao,
                    ReviewedID = review.Reviewed.Id,
                    ReviewedName = review.Reviewed.Nome,
                    ReviewerID = review.Reviewer.Id,
                    ReviewerName = review.Reviewer.Nome,
                    Data = review.Data
                });
            }

            return listaReviews;
        }

        /// <summary>
        /// Meotodo para obter media das reviews do Funcionário
        /// </summary>
        /// <param name="id">Id de Funcionário que queremos obter a media</param>
        /// <returns></returns>
        [HttpGet("mediaDeReviewsFuncionario/{id}")]
        //[Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<double>> getMediaReviews(int id)
        {
            var funcionario = await _context.Funcionarios.FindAsync(id);

            int count = 0;
            double media = 0;

            foreach(var r in funcionario.ListaDeReviews)
            {
                media = media + r.Cotacao;
                count++;
            }
           
            double temp = media/count;
            return temp;
        }



        /// <summary>
        /// Este metodo vai retornar todos Funcionarios não aceites pelo o administrador
        /// </summary>
        /// <returns>A lista de Utilizadores não aceites pelo administrador</returns>
        [HttpGet("FuncionariosNaoAceites")]
        public async Task<List<FuncionarioDetails>> ListaDeFuncionariosNaoAceites()
        {
            var listaDeUtilizadores = await _context.Funcionarios.ToListAsync();

            var listaDeUtilizadoresNaoAceites = new List<FuncionarioDetails>();


            foreach (var c in listaDeUtilizadores)
            {
                if (c.Aceite == false)
                {
                    var image = imageToByteArray(c.Imagem, c.Email);
                    FuncionarioDetails utilizador = new FuncionarioDetails
                    {
                        Id = c.Id,
                        Nome = c.Nome,
                        Imagem = image,
                        Morada = c.Morada,
                        Cadastro = c.Cadastro,
                        HistoricoMedico = c.HistoricoMedico,
                        CartaDeConducao = c.CartaDeConducao,
                        CvFile = c.CvFile,
                        CcFile = c.CcFile,
                        Email = c.Email,
                        Idade = c.Idade,
                        Preco = c.Preco,
                        TipoLimpeza = c.TipoLimpeza.ToString(),
                        TipoUtilizador = c.Cargo.ToString()
                    };
                    listaDeUtilizadoresNaoAceites.Add(utilizador);
                }
            }

            return listaDeUtilizadoresNaoAceites;
        }


        
        

        /// <summary>
        /// Este metodo vai retornar todos Funcionarios aceites pelo o administrador
        /// </summary>
        /// <returns>A lista de Utilizadores não aceites pelo administrador</returns>
        [HttpGet("FuncionariosAceites")]
        public async Task<List<FuncionarioDetails>> ListaDeFuncionariosAceites()
        {
            var listaDeUtilizadores = await _context.Funcionarios.ToListAsync();

            var listaDeUtilizadoresNaoAceites = new List<FuncionarioDetails>();


            foreach (var c in listaDeUtilizadores)
            {
                if (c.Aceite == true)
                {
                    var image = imageToByteArray(c.Imagem, c.Email);
                    FuncionarioDetails utilizador = new FuncionarioDetails
                    {
                        Id = c.Id,
                        Nome = c.Nome,
                        Imagem = image,
                        Morada = c.Morada,
                        Cadastro = c.Cadastro,
                        HistoricoMedico = c.HistoricoMedico,
                        CartaDeConducao = c.CartaDeConducao,
                        CvFile = c.CvFile,
                        CcFile = c.CcFile,
                        Email = c.Email,
                        Idade = c.Idade,
                        Preco = c.Preco,
                        TipoLimpeza = c.TipoLimpeza.ToString(),
                        TipoUtilizador = c.Cargo.ToString()
                    };
                    listaDeUtilizadoresNaoAceites.Add(utilizador);
                }
            }

            return listaDeUtilizadoresNaoAceites;
        }


        /// <summary>
        /// Este método vai retornar um Cliente especifico
        /// </summary>
        /// <param name="id">Id do Cliente que desejamos obter</param>
        /// <returns>NotFound caso o Cliente não exista</returns>
        /// <returns>Ok caso o Cliente exista</returns>
        // GET: api/Clientes/5
        [HttpGet("findFuncionarioByEmail/{email}")]
        //[Authorize(Roles ="Cliente, Funcionario")]
        public async Task<ActionResult<FuncionarioDetails>> GetFuncionario(string email)
        {
            var ListaFuncionario = await _context.Funcionarios.ToListAsync();

            Funcionario funcionario = new Funcionario();

            foreach(var funcionarioTemp in ListaFuncionario)
            {
                if (funcionarioTemp.Email.Equals(email))
                {
                    funcionario = funcionarioTemp;
                }
            }
            
            var imagem = imageToByteArray(funcionario.Imagem, funcionario.Email);

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
                CvFile = funcionario.CvFile,
                TipoUtilizador = funcionario.Cargo.ToString(),
                Preco = funcionario.Preco,
                TipoLimpeza = funcionario.TipoLimpeza.ToString()
            };

            return Ok(funcionarioDetails);
        }

        /// <summary>
        /// Metodo que verifica se determinado Funcionario existe
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        private bool FuncionarioExists(int id)
        {
            return _context.Funcionarios.Any(e => e.Id == id);
        }
    }
}
