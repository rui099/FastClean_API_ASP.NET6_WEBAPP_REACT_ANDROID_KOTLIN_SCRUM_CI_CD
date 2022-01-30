using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using FastCleanAPI.Models;
using FastCleanAPI.DTO_s.Reports;
using FastCleanAPI.DTO_s.ReportsDTO;
using Microsoft.AspNetCore.Authorization;

namespace FastCleanAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ReportClientesController : ControllerBase
    {
        private readonly FastCleanContext _context;

        /// <summary>
        /// Metodo Construtor
        /// </summary>
        /// <param name="context"></param>
        public ReportClientesController(FastCleanContext context)
        {
            _context = context;
        }

       

        /// <summary>
        /// Metodo retornar um report do Cliente
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        // GET: api/ReportClientes/5
        [HttpGet("{id}")]
        [Authorize("Administrador")]
        public async Task<ActionResult<ReportsDetailsDTO>> GetReportCliente(int id)
        {
            if (id == null)
            {
                return NotFound("Este Id não existe.");
            }

            var reportCliente = await _context.ReportsClientes.FindAsync(id);

            if (reportCliente == null)
            {
                return NotFound("Este Cliente não existe?");
            }

            ReportsDetailsDTO detailsDTO = new ReportsDetailsDTO
            {
                Cliente = reportCliente.Reported.Nome,
                Funcionario = reportCliente.Reporter.Nome,
                Titulo = reportCliente.Titulo,
                Tipo = reportCliente.Tipo.ToString(),
                Descricao = reportCliente.Descricao,
                Arquivado = reportCliente.Arquivado.ToString()
            };

            return detailsDTO;
        }

       
        /// <summary>
        /// Metodo para criar um report do Cliente mas do Chat
        /// </summary>
        /// <param name="reportDTO"></param>
        /// <returns></returns>
        // POST: api/ReportClientes/Chat
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("Chat")]
        //[Authorize("Funcionario")]
        public async Task<ActionResult<ReportCliente>> PostReportClienteChat(ReportDTOCliente reportDTO)
        {

            var foundCliente = await _context.Clientes.FindAsync(reportDTO.Reported);
            var foundFuncionario = await _context.Funcionarios.FindAsync(reportDTO.Reporter);
            
            if(foundCliente == null || foundFuncionario == null)
            {
                return NotFound("Um dos utilizadores não existe");
            }

            var reportCliente = new ReportCliente
            {
                Titulo = reportDTO.Titulo,
                Descricao = reportDTO.Descricao,
                Tipo = TipoReport.Chat,
                Reported = foundCliente,
                Reporter = foundFuncionario,
                Arquivado = false
            };

            _context.ReportsClientes.Add(reportCliente);

            await _context.SaveChangesAsync();

            return reportCliente;

        }

        
        /// <summary>
        /// Metodo para criar um report do Cliente mas do Servico
        /// </summary>
        /// <param name="reportDTO"></param>
        /// <returns></returns>
        // POST: api/ReportCliente/Servico
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("Servico")]
        //[Authorize("Funcionario")]
        public async Task<ActionResult<ReportCliente>> PostReportClienteServico(ReportDTOCliente reportDTO)
        {

            var foundCliente = await _context.Clientes.FindAsync(reportDTO.Reported);
            var foundFuncionario = await _context.Funcionarios.FindAsync(reportDTO.Reporter);



            if (foundCliente != null || foundFuncionario != null)
            {
                var reportCliente = new ReportCliente
                {
                    Titulo = reportDTO.Titulo,
                    Descricao = reportDTO.Descricao,
                    Tipo = TipoReport.Servico,
                    Reported = foundCliente,
                    Reporter = foundFuncionario,
                    Arquivado = false
                };

                _context.ReportsClientes.Add(reportCliente);

                await _context.SaveChangesAsync();

                return reportCliente;
            }
            else
            {
                return BadRequest("Não foi possível criar um report do cliente");
            }

        }

        

        

       
    }
}
