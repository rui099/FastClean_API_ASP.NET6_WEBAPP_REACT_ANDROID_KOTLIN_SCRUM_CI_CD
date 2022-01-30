using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using FastCleanAPI.Models;
using FastCleanAPI.DTO_s.Marcacao;
using Microsoft.AspNetCore.Authorization;
using FastCleanAPI.DTO_s.Funcionario;
using System.Runtime.InteropServices;
using System.Globalization;

namespace FastCleanAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class MarcacoesController : ControllerBase
    {
        private readonly FastCleanContext _context;

        /// <summary>
        /// Metodo construtor 
        /// </summary>
        /// <param name="context"></param>
        public MarcacoesController(FastCleanContext context)
        {
            _context = context;
        }

        


        /// <summary>
        /// Metodo que retorna as Marcações Terminadas
        /// </summary>
        /// <returns></returns>
        // GET: api/Marcacoes
        [HttpGet("Terminadas")]
        //[Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<IEnumerable<MarcacaoDTO>>> GetMarcacoesTerminadas()
        {

            var Listmarcacao = await _context.Marcacoes.ToListAsync();

            List<MarcacaoDTO> listaDeMarcacaoes = new List<MarcacaoDTO>();

            foreach (var marcacao in Listmarcacao)
            {
                if (marcacao.Terminada == true)
                {
                    listaDeMarcacaoes.Add(new MarcacaoDTO
                    {
                        MarcacaoId = marcacao.MarcacaoId,
                        TipoImovel = marcacao.TipoImovel.ToString(),
                        TipoLimpeza = marcacao.TipoLimpeza.ToString(),
                        TipoAgendamento = marcacao.TipoAgendamento.ToString(),
                        NumQuartos = marcacao.NumQuartos,
                        NumCasasDeBanho = marcacao.NumCasasDeBanho,
                        Cozinha = marcacao.Cozinha.ToString(),
                        Sala = marcacao.Sala.ToString(),
                        Detalhes = marcacao.Detalhes,
                        DiaHora = marcacao.DiaHora!,
                        HoraInicio = marcacao.HoraInicial,
                        HoraFinal = marcacao.HoraFinal,
                        ClienteID = marcacao.Cliente.Id,
                        Cliente = marcacao.Cliente.Nome,
                        FuncionarioID = marcacao.Funcionario.Id,
                        Funcionario = marcacao.Funcionario.Nome,
                        MarcacaoAceitePeloCliente = marcacao.MarcacaoAceitePeloCliente.ToString(),
                        MarcacaoAceitePeloFunc = marcacao.MarcacaoAceitePeloFunc.ToString(),
                        NumHorasPrevistas = marcacao.NumHorasPrevistas,
                        MoradaMarcacao = marcacao.MoradaMarcacao,
                        LatitudeMarcacao = marcacao.LatitudeMarcacao,
                        LongitudeMarcacao = marcacao.LongitudeMarcacao,
                        Terminada = marcacao.Terminada.ToString(),
                        Duracao = marcacao.DuracaoTotal!,
                        Total = marcacao.Total,
                        ReviewCliente = marcacao.CliReview,
                        ReviewFuncionario = marcacao.FuncReview
                    });
                }
            }

            return listaDeMarcacaoes;

        }


        /// <summary>
        /// Metodo que retorna as Marcações a Decorrer
        /// </summary>
        /// <returns></returns>
        // GET: api/Marcacoes
        [HttpGet("ADecorrer")]
        //[Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<IEnumerable<MarcacaoDTO>>> GetMarcacoesDecorrer()
        {

            var Listmarcacao = await _context.Marcacoes.ToListAsync();

            List<MarcacaoDTO> listaDeMarcacaoes = new List<MarcacaoDTO>();

            foreach (var marcacao in Listmarcacao)
            {
                if (marcacao.Terminada == false && marcacao.HoraInicial!=null)
                {
                    listaDeMarcacaoes.Add(new MarcacaoDTO
                    {
                        MarcacaoId = marcacao.MarcacaoId,
                        TipoImovel = marcacao.TipoImovel.ToString(),
                        TipoLimpeza = marcacao.TipoLimpeza.ToString(),
                        TipoAgendamento = marcacao.TipoAgendamento.ToString(),
                        NumQuartos = marcacao.NumQuartos,
                        NumCasasDeBanho = marcacao.NumCasasDeBanho,
                        Cozinha = marcacao.Cozinha.ToString(),
                        Sala = marcacao.Sala.ToString(),
                        Detalhes = marcacao.Detalhes,
                        DiaHora = marcacao.DiaHora!,
                        HoraInicio = marcacao.HoraInicial,
                        HoraFinal = marcacao.HoraFinal,
                        ClienteID = marcacao.Cliente.Id,
                        Cliente = marcacao.Cliente.Nome,
                        FuncionarioID = marcacao.Funcionario.Id,
                        Funcionario = marcacao.Funcionario.Nome,
                        MarcacaoAceitePeloCliente = marcacao.MarcacaoAceitePeloCliente.ToString(),
                        MarcacaoAceitePeloFunc = marcacao.MarcacaoAceitePeloFunc.ToString(),
                        NumHorasPrevistas = marcacao.NumHorasPrevistas,
                        MoradaMarcacao = marcacao.MoradaMarcacao,
                        LatitudeMarcacao = marcacao.LatitudeMarcacao,
                        LongitudeMarcacao = marcacao.LongitudeMarcacao,
                        Terminada = marcacao.Terminada.ToString(),
                        Duracao = marcacao.DuracaoTotal!,
                        Total = marcacao.Total,
                        ReviewCliente = marcacao.CliReview,
                        ReviewFuncionario = marcacao.FuncReview
                    });
                }
            }

            return listaDeMarcacaoes;

        }

        /// <summary>
        /// Metodo que retorna as Marcações a Decorrer
        /// </summary>
        /// <returns></returns>
        // GET: api/Marcacoes
        [HttpGet("NaoAceites")]
        //[Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<IEnumerable<MarcacaoDTO>>> GetMarcacoesAindaNaoAceites()
        {

            var Listmarcacao = await _context.Marcacoes.ToListAsync();

            List<MarcacaoDTO> listaDeMarcacaoes = new List<MarcacaoDTO>();

            foreach (var marcacao in Listmarcacao)
            {
                if (marcacao.MarcacaoAceitePeloFunc == false)
                {
                    listaDeMarcacaoes.Add(new MarcacaoDTO
                    {
                        MarcacaoId = marcacao.MarcacaoId,
                        TipoImovel = marcacao.TipoImovel.ToString(),
                        TipoLimpeza = marcacao.TipoLimpeza.ToString(),
                        TipoAgendamento = marcacao.TipoAgendamento.ToString(),
                        NumQuartos = marcacao.NumQuartos,
                        NumCasasDeBanho = marcacao.NumCasasDeBanho,
                        Cozinha = marcacao.Cozinha.ToString(),
                        Sala = marcacao.Sala.ToString(),
                        Detalhes = marcacao.Detalhes,
                        DiaHora = marcacao.DiaHora!,
                        HoraInicio = marcacao.HoraInicial,
                        HoraFinal = marcacao.HoraFinal,
                        ClienteID = marcacao.Cliente.Id,
                        Cliente = marcacao.Cliente.Nome,
                        FuncionarioID = marcacao.Funcionario.Id,
                        Funcionario = marcacao.Funcionario.Nome,
                        MarcacaoAceitePeloCliente = marcacao.MarcacaoAceitePeloCliente.ToString(),
                        MarcacaoAceitePeloFunc = marcacao.MarcacaoAceitePeloFunc.ToString(),
                        NumHorasPrevistas = marcacao.NumHorasPrevistas,
                        MoradaMarcacao = marcacao.MoradaMarcacao,
                        LatitudeMarcacao = marcacao.LatitudeMarcacao,
                        LongitudeMarcacao = marcacao.LongitudeMarcacao,
                        Terminada = marcacao.Terminada.ToString(),
                        Duracao = marcacao.DuracaoTotal!,
                        Total = marcacao.Total,
                        ReviewCliente = marcacao.CliReview,
                        ReviewFuncionario = marcacao.FuncReview
                    });
                }
            }

            return listaDeMarcacaoes;

        }

        /// <summary>
        /// Metodo que retorna uma marcação especifica
        /// </summary>
        /// <param name="id">Id da marcação que queremos ver os detalhes</param>
        /// <returns></returns>
        // GET: api/Marcacoes/5
        [HttpGet("{id}")]
        //[Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<MarcacaoDTO>> GetMarcacao(int id)
        {
            Marcacao marcacao = await _context.Marcacoes.FindAsync(id);

            if (marcacao == null)
            {
                return NotFound("Esta marcação não existe!!!");
            }

            MarcacaoDTO details = new MarcacaoDTO
            {
                MarcacaoId = marcacao.MarcacaoId,
                TipoImovel = marcacao.TipoImovel.ToString(),
                TipoLimpeza = marcacao.TipoLimpeza.ToString(),
                TipoAgendamento = marcacao.TipoAgendamento.ToString(),
                NumQuartos = marcacao.NumQuartos,
                NumCasasDeBanho = marcacao.NumCasasDeBanho,
                Cozinha = marcacao.Cozinha.ToString(),
                Sala = marcacao.Sala.ToString(),
                Detalhes = marcacao.Detalhes,
                DiaHora = marcacao.DiaHora!,
                HoraInicio = marcacao.HoraInicial,
                HoraFinal = marcacao.HoraFinal,
                ClienteID = marcacao.Cliente.Id,
                Cliente = marcacao.Cliente.Nome,
                FuncionarioID = marcacao.Funcionario.Id,
                Funcionario = marcacao.Funcionario.Nome,
                MarcacaoAceitePeloCliente = marcacao.MarcacaoAceitePeloCliente.ToString(),
                MarcacaoAceitePeloFunc = marcacao.MarcacaoAceitePeloFunc.ToString(),
                NumHorasPrevistas = marcacao.NumHorasPrevistas,
                MoradaMarcacao = marcacao.MoradaMarcacao,
                LatitudeMarcacao = marcacao.LatitudeMarcacao,
                LongitudeMarcacao = marcacao.LongitudeMarcacao,
                Terminada = marcacao.Terminada.ToString(),
                Duracao = marcacao.DuracaoTotal!,
                Total = marcacao.Total,
                ReviewCliente = marcacao.CliReview,
                ReviewFuncionario = marcacao.FuncReview,
                EstadoFuncionario = marcacao.Funcionario.Estado.ToString()
            };


            return Ok(details);
        }

        /// <summary>
        /// Metodo que termina a Marcação
        /// </summary>
        /// <param name="id">Id da marcação a ser alterada</param>
        /// <returns>NotFound caso a marcação não exista</returns>
        /// <returns>NotFound a marcação não tenha Funcionário atribuido</returns>
        /// <returns>Ok caso a marcação terminada com sucesso, introduzindo a horaFinal, a duração total e o total a pagar</returns>
        // PUT: api/Marcacoes/Terminada/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("Terminada/{id}")]
        //[Authorize("Funcionario")]
        public async Task<IActionResult> PutTerminarMarcacao(int id)
        {
            var marcacao = await _context.Marcacoes.FindAsync(id);

            if (marcacao == null)
            {
                return NotFound("Esta Marcação não existe!!!");
            }

            if(marcacao.HoraInicial == null)
            {
                return BadRequest("Esta Marcação ainda não começou!");
            }

            if(marcacao.MarcacaoAceitePeloFunc == false)
            {
                return BadRequest("Não pode ser terminada porque não tem Funcionário associado.");
            }

            DateTime final = DateTime.Now;
            marcacao.Funcionario.Estado = EstadoFuncionario.Disponível;
            marcacao.HoraFinal = String.Format("{0:HH:mm}", final);
            marcacao.Terminada = true;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!MarcacaoExists(id))
            {
                return NotFound();
            }

            DateTime horaIni = Convert.ToDateTime(marcacao.HoraInicial);

            DateTime horaFinal = Convert.ToDateTime(marcacao.HoraFinal);

            TimeSpan hourDuration = horaFinal - horaIni;

            double time = 0;

            time = Math.Abs(hourDuration.Hours + ((double) hourDuration.Minutes/100));

            marcacao.DuracaoTotal = time.ToString();
            marcacao.Total = time * marcacao.Funcionario.Preco;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!MarcacaoExists(id))
            {
                return NotFound();
            }
            return Ok(marcacao);
        }


        /// <summary>
        /// Mudar para True o reviewFuncionario na marcacão
        /// </summary>
        /// <param name="id">Id da marcação</param>
        /// <returns></returns>
        [HttpPut("reviewFuncionario/{id}")]
        //[Authorize("Funcionario")]
        public async Task<IActionResult> PutReviewFuncionario(int id)
        {

            var marcacao = await _context.Marcacoes.FindAsync(id);

            if (marcacao == null)
            {
                return NotFound("Esta Marcação não existe!!!");
            }


            marcacao.FuncReview = true;
            

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!MarcacaoExists(id))
            {
                return NotFound();
            }
            return Ok(marcacao);
        }


        /// <summary>
        /// Este metodo vai possibilitar 
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpPut("reviewCliente/{id}")]
        //[Authorize("Funcionario")]
        public async Task<IActionResult> PutReviewCliente(int id)
        {

            var marcacao = await _context.Marcacoes.FindAsync(id);

            if (marcacao == null)
            {
                return NotFound("Esta Marcação não existe!!!");
            }


            marcacao.CliReview = true;


            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!MarcacaoExists(id))
            {
                return NotFound();
            }
            return Ok(marcacao);
        }

        /// <summary>
        /// Metodo que vai iniciar o servico da Marcação
        /// </summary>
        /// <param name="id">Id da Marcação que desejamos começar</param>
        /// <returns>NotFound caso a marcação não exista</returns>
        /// <returns>NotFound caso não tenha funcionário inserido</returns>
        /// <returns>Ok retorna a marcação com a horaInicial que é um DateTime.Now que indica o tempo naquele determinado instante</returns>
        [HttpPut("InicioServico/{id}")]
        public async Task<IActionResult> PutInicioServicoMarcacao(int id)
        {

            var marcacao = await _context.Marcacoes.FindAsync(id);

            if(marcacao == null)
            {
                return NotFound("Esta marcação não existe!!!");
            }

            if(marcacao.HoraInicial != null)
            {
                return BadRequest("Esta marcação já começou!!!");
            }

            if(marcacao.Funcionario == null)
            {
                return NotFound("Este Funcionário não existe, logo não pode terminar a marcação!!!");
            }

            if (marcacao.MarcacaoAceitePeloFunc == false)
            {
                return BadRequest("Esta Marcação ainda não foi aceite por um Funcionário!!");
            }

            marcacao.Funcionario.Estado = EstadoFuncionario.Ocupado;
            DateTime inicio = DateTime.Now;
            marcacao.HoraInicial = String.Format("{0:HH:mm}", inicio);

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!MarcacaoExists(id))
            {
                return NotFound();
            }
            return Ok(marcacao.HoraInicial);
        }


        /// <summary>
        /// Metodo para alterar o estado de uma Marcação para aceite
        /// </summary>
        /// <param name="id">Id da Marcação</param>
        /// <param name="idFuncionario">Id do Funcionário</param>
        /// <returns></returns>
        // PUT: api/Marcacoes/FuncAceite/5/2
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("FuncAceite/{id}/{idFuncionario}")]
        //[Authorize("Funcionario")]
        public async Task<IActionResult> PutFuncAceitarMarcacao(int id, int idFuncionario)
        {

            var marcacao = await _context.Marcacoes.FindAsync(id);
            Funcionario funcionario = await _context.Funcionarios.FindAsync(idFuncionario); 

            if (marcacao == null)
            {
                return NotFound("Esta Marcação não existe!!!");
            }
            if(funcionario == null)
            {
                return NotFound("Este Funcionário não existe");
            }

            if(marcacao.Funcionario.Id != idFuncionario)
            {
                return BadRequest("Este Funcionário não foi o escolhido para realizar a marcação.");
            }

            if(funcionario.Aceite == false)
            {
                return BadRequest("Este Funcionário não pode ser adicionado a marcação porque ainda não esta aceite pelo Administrador!!");
            }

            marcacao.MarcacaoAceitePeloFunc = true;
            
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!MarcacaoExists(id))
            {
                return NotFound();
            }
            return Ok(marcacao.Funcionario);
        }


        /// <summary>
        /// Este metodo vai rejeitar uma marcação por parte do Funcionário
        /// </summary>
        /// <param name="id">Id Da marcação</param>
        /// <param name="idFuncionario">Id do Funcionário</param>
        /// <returns>Not Found caso o Funcionário não exista</returns>
        /// <returns>Not Found caso a Marcação não exista</returns>
        /// <returns>BadRequest caso o ID do Funcionário não coencida com o ID que esta na marcação.</returns>
        /// <returns>BadRequest caso a Marcação já esteja aceite pelo o Funcionário</returns>
        /// 
        [HttpDelete("rejectMarcacao/{id}/{idFuncionario}")]
        public async Task<IActionResult> rejectMarcacao(int id,int idFuncionario)
        {
            Marcacao marc = await _context.Marcacoes.FindAsync(id);
            Funcionario func = await _context.Funcionarios.FindAsync(idFuncionario);

            if(func == null)
            {
                return NotFound("Este Funcionário não existe!!");
            }

            if (marc == null)
            {
                return NotFound("Esta marcação não existe!!!");
            }

            if (marc.Funcionario.Id != idFuncionario)
            {
                return BadRequest("Este Funcionário não foi escolhido para realizar esta marcação logo não pode eliminar esta marcação.");
            }

            if (marc.MarcacaoAceitePeloFunc == true && marc.Terminada== true)
            {
                return BadRequest("Esta marcação já foi aceite e não pode ser rejeitada.");
            }

            _context.Marcacoes.Remove(marc);
            await _context.SaveChangesAsync();

            return Ok("Esta Marcação foi rejeitada!!!");
        }


        /// <summary>
        /// Metodo para criar uma marcação com agendamento agora na Morada do Cliente
        /// </summary>
        /// <param name="marcacaoDTO">DTO para realizar marcação do tipo Casa ou Apartamento</param>
        /// <returns>NotFound caso o cliente não exista</returns>
        /// <returns>Marcação que foi criada</returns>
        // POST: api/Marcacoes/Agora
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("Agora")]
        //[Authorize("Cliente")]
        public async Task<ActionResult<MarcacaoCasaApartamento>> PostMarcacaoAgora(MarcacaoCasaApartamento marcacaoDTO)
        {

            Cliente foundCliente = await _context.Clientes.FindAsync(marcacaoDTO.Cliente);
            Funcionario foundFuncionario = await _context.Funcionarios.FindAsync(marcacaoDTO.Funcionario);

            if(foundCliente == null)
            {
                return NotFound("Este Cliente não existe.");
            }

            if(foundFuncionario == null)
            {
                return NotFound("Este funcionario não existe.");
            }

            if(foundFuncionario.Aceite == false)
            {
                return BadRequest("Este Funcionário ainda não foi aceite logo não pode efetuar marcações!!");
            }

            if(foundCliente.Aceite == false)
            {
                return BadRequest("Este Cliente ainda não foi aceite logo não pode efetuar marcações!!");
            }

            DateTime agora = DateTime.Now;
            string now = String.Format("{0:dd/MM/yyyy HH:mm}", agora);

            Marcacao marcacao = new Marcacao
            {
                TipoLimpeza = marcacaoDTO.TipoLimpeza,
                TipoAgendamento = TipoAgendamento.Agora,
                NumQuartos = marcacaoDTO.NumQuartos,
                NumCasasDeBanho = marcacaoDTO.NumCasasDeBanho,
                Cozinha = marcacaoDTO.Cozinha,
                Detalhes = marcacaoDTO.Detalhes,
                DiaHora = now,
                Sala = marcacaoDTO.Sala,
                Cliente = foundCliente,
                Funcionario = foundFuncionario,
                MoradaMarcacao = marcacaoDTO.MoradaMarcacao,
                LongitudeMarcacao = marcacaoDTO.LongitudeMarcacao,
                LatitudeMarcacao = marcacaoDTO.LatitudeMarcacao,
                NumHorasPrevistas = marcacaoDTO.NumHorasPrevistas,
                Terminada = false,
                MarcacaoAceitePeloCliente = true,
                MarcacaoAceitePeloFunc = false,
                FuncReview = false,
                CliReview = false
            };

           
            _context.Marcacoes.Add(marcacao);
            _context.SaveChanges();

            return Ok(marcacao);
        }


       

       

        /// <summary>
        /// Método que vai efetuar uma marcação com agendar_hora mas com uma morada que não é onde habita o cliente
        /// </summary>
        /// <param name="marcacaoDTO">DTO para efetuar uma marcação</param>
        /// <returns>Not Found caso o Cliente não exista</returns>
        /// <returns>Ok caso a marcação seja criada com sucesso</returns>
        /// <returs>BadRequest caso o Cliente ainda não esteja aceite para usar a aplicação a 100%</returs>
        [HttpPost("Agenda_Hora")]
        public async Task<ActionResult<MarcacaoCasaApartamento>> PostMarcacaoAgendar_Hora(MarcacaoCasaApartamento marcacaoDTO)
        {

            Cliente foundCliente = await _context.Clientes.FindAsync(marcacaoDTO.Cliente);
            Funcionario foundFuncionario = await _context.Funcionarios.FindAsync(marcacaoDTO.Funcionario);

            if (foundCliente == null)
            {
                return NotFound("Este Cliente não existe!!!");
            }

            if (foundCliente.Aceite == false)
            {
                return BadRequest("Este Cliente ainda não foi aceite logo não pode efetuar marcações!!!");
            }

            if(foundFuncionario == null)
            {
                return NotFound("Este Funcionário não existe!!");
            }

            if(foundFuncionario.Aceite == false)
            {
                return BadRequest("Este Funcionário ainda não foi aceite logo não pode aceitar ");
            }

          
            Marcacao marcacao = new Marcacao
            {
                TipoLimpeza = marcacaoDTO.TipoLimpeza,
                TipoAgendamento = TipoAgendamento.Agendar_Hora,
                NumQuartos = marcacaoDTO.NumQuartos,
                NumCasasDeBanho = marcacaoDTO.NumCasasDeBanho,
                Cozinha = marcacaoDTO.Cozinha,
                Detalhes = marcacaoDTO.Detalhes,
                DiaHora = marcacaoDTO.DiaHora,
                MoradaMarcacao = marcacaoDTO.MoradaMarcacao,
                LatitudeMarcacao = marcacaoDTO.LatitudeMarcacao,
                LongitudeMarcacao = marcacaoDTO.LongitudeMarcacao,
                Sala = marcacaoDTO.Sala,
                Cliente = foundCliente,
                Funcionario = foundFuncionario,
                NumHorasPrevistas = marcacaoDTO.NumHorasPrevistas,
                Terminada = false,
                MarcacaoAceitePeloCliente = true,
                MarcacaoAceitePeloFunc = false,
                CliReview = false,
                FuncReview = false
            };

            _context.Marcacoes.Add(marcacao);
            _context.SaveChanges();

            return Ok(marcacao);

        }

       


       



        /// <summary>
        /// Metodo que vai retorna a listagem de Funcionarios que sejam do tipo Normal e a sua distancia ao Cliente seja dentro do range
        /// </summary>
        /// <param name="idCliente">Id do Cliente</param>
        /// <param name="range">Range de Km que queremos saber onde estão os Funcionarios</param>
        /// <returns>NotFound, caso o Cliente não exista </returns>
        /// <returns>BadRequest, caso não existem Funcionários disponiveis</returns>
        /// <returns>A lista de Funcionarios disponiveis e dentro dos ranges</returns>
        [HttpGet("FuncionarioDistanciaNormal/{latitude}/{longitude}/{range}")]
        public async Task<ActionResult<IEnumerable<FuncionarioDetails>>> FiltrarFuncionarioNormalPorDistanciaComLatLon(double range,string latitude, string longitude)
        {
            var Funcionarios = await _context.Funcionarios.ToListAsync();

            

            List<Funcionario> allFuncionarios = new List<Funcionario>();

            foreach (var funcionario in Funcionarios)
            {
                if ((int)funcionario.Estado != 0 && (int)funcionario.TipoLimpeza == 1 && funcionario.Subscricao == true)
                {
                    allFuncionarios.Add(funcionario);
                }
            }

            if (allFuncionarios.Count == 0)
            {
                return BadRequest("Não existe Funcionários disponiveis");
            }

            List<FuncionarioDetails> listaDeFuncionarios = new List<FuncionarioDetails>();

            foreach (var i in allFuncionarios)
            {
                double clienteLatitude = ConvertToDouble(latitude);
                double clienteLongitude = ConvertToDouble(longitude);

                double funcionarioLatitude = ConvertToDouble(i.Latitude);
                double funcionarioLongitude = ConvertToDouble(i.Longitude);

                System.Device.Location.GeoCoordinate source = new System.Device.Location.GeoCoordinate(clienteLatitude, clienteLongitude);
                System.Device.Location.GeoCoordinate destination = new System.Device.Location.GeoCoordinate(funcionarioLatitude, funcionarioLongitude);

                double distanceTemp = source.GetDistanceTo(destination);

                double distance = Math.Round((distanceTemp / 1000), 2);

                double mediasReviews = GetMediaReviews(i);

                if (distance <= range)
                {
                    listaDeFuncionarios.Add(new FuncionarioDetails
                    {
                        Id = i.Id,
                        Nome = i.Nome,
                        TipoLimpeza = i.TipoLimpeza.ToString(),
                        Email = i.Email,
                        Idade = i.Idade,
                        Preco = i.Preco,
                        distanciaAoCliente = distance,
                        MediaReviews = mediasReviews,
                        Latitude = i.Latitude,
                        Longitude = i.Longitude
                    });
                }

            }
            return listaDeFuncionarios;
        }


        
        /// <summary>
        /// Metodo para calcular a média das reviews de um Funcionário
        /// </summary>
        /// <param name="funcionario">Funcionário que queremos obter uma </param>
        /// <returns></returns>
        public double GetMediaReviews(Funcionario funcionario)
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
        /// Metodo que vai retorna a listagem de Funcionarios que sejam do tipo Professional e a sua distancia ao Cliente seja dentro do range
        /// </summary>
        /// <param name="idCliente">Id do Cliente</param>
        /// <param name="range">Range de Km que queremos saber onde estão os Funcionarios</param>
        /// <returns>NotFound, caso o Cliente não exista </returns>
        /// <returns>BadRequest, caso não existem Funcionários disponiveis</returns>
        /// <returns>A lista de Funcionarios disponiveis e dentro dos ranges</returns>
        [HttpGet("FuncionarioDistanciaProfessional/{latitude}/{longitude}/{range}")]
        public async Task<ActionResult<IEnumerable<FuncionarioDetails>>> FiltrarFuncionarioProfessionalPorDistancia(double range, string latitude, string longitude)
        {
            var Funcionarios = await _context.Funcionarios.ToListAsync();

            List<Funcionario> allFuncionarios = new List<Funcionario>();

            foreach (var funcionario in Funcionarios)
            {
                if (((int)funcionario.Estado != 0 && (int)funcionario.TipoLimpeza == 0) && funcionario.Subscricao == true)
                {
                    allFuncionarios.Add(funcionario);
                }
            }

            if (allFuncionarios.Count == 0)
            {
                return BadRequest("Não existe Funcionários disponiveis");
            }

            List<FuncionarioDetails> listaDeFuncionarios = new List<FuncionarioDetails>();

            foreach (var i in allFuncionarios)
            {
                double clienteLatitude = ConvertToDouble(latitude);
                double clienteLongitude = ConvertToDouble(longitude);

                double funcionarioLatitude = ConvertToDouble(i.Latitude);
                double funcionarioLongitude = ConvertToDouble(i.Longitude);

                System.Device.Location.GeoCoordinate source = new System.Device.Location.GeoCoordinate(clienteLatitude, clienteLongitude);
                System.Device.Location.GeoCoordinate destination = new System.Device.Location.GeoCoordinate(funcionarioLatitude, funcionarioLongitude);

                double distanceTemp = source.GetDistanceTo(destination);


                double distance = Math.Round((distanceTemp / 1000), 2);

                double mediaDasReviews = GetMediaReviews(i);

                if (distance <= range)
                {
                    listaDeFuncionarios.Add(new FuncionarioDetails
                    {
                        Id = i.Id,
                        Nome = i.Nome,
                        TipoLimpeza = i.TipoLimpeza.ToString(),
                        Email = i.Email,
                        Idade = i.Idade,
                        Preco = i.Preco,
                        distanciaAoCliente = distance,
                        Latitude = i.Latitude,
                        Longitude = i.Longitude,
                        MediaReviews = mediaDasReviews
                    });
                }

            }
            return listaDeFuncionarios;
        }

        /// <summary>
        /// Metodo que converte um string em Double
        /// </summary>
        /// <param name="s"></param>
        /// <returns></returns>
        /// <exception cref="Exception"></exception>
        private double ConvertToDouble(string s)
        {
            char systemSeparator = Thread.CurrentThread.CurrentCulture.NumberFormat.CurrencyDecimalSeparator[0];
            double result = 0;
            try
            {
                if (s != null)
                    if (!s.Contains(","))
                        result = double.Parse(s, CultureInfo.InvariantCulture);
                    else
                        result = Convert.ToDouble(s.Replace(".", systemSeparator.ToString()).Replace(",", systemSeparator.ToString()));
            }
            catch (Exception e)
            {
                try
                {
                    result = Convert.ToDouble(s);
                }
                catch
                {
                    try
                    {
                        result = Convert.ToDouble(s.Replace(",", ";").Replace(".", ",").Replace(";", "."));
                    }
                    catch
                    {
                        throw new Exception("Wrong string-to-double format");
                    }
                }
            }
            return result;
        }


        /// <summary>
        /// Metodo que verifica se uma marcação existe
        /// </summary>
        /// <param name="id">Id da Marcação</param>
        /// <returns>True se a marcação existir</returns>
        /// <returns>False se a marcação não existir na base de dados</returns>
        private bool MarcacaoExists(int id)
        {
            return _context.Marcacoes.Any(e => e.MarcacaoId == id);
        }
    }
}
