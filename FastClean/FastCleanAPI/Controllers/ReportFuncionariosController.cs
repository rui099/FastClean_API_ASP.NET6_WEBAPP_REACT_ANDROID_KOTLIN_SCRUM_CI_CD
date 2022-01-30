using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
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
    public class ReportFuncionariosController : ControllerBase
    {
        private readonly FastCleanContext _context;

        /// <summary>
        /// Metodo Construtor
        /// </summary>
        /// <param name="context"></param>
        public ReportFuncionariosController(FastCleanContext context)
        {
            _context = context;
        }

        
        /// <summary>
        /// Metodo para retornar um report de um Funcionario
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        // GET: api/ReportFuncionarios/5
        [HttpGet("{id}")]
        [Authorize("Administrador")]
        public async Task<ActionResult<ReportsDetailsDTO>> GetReportFuncionario(int id)
        {
            var reportFuncionario = await _context.ReportsFuncionario.FindAsync(id);

            if (reportFuncionario == null)
            {
                return NotFound("Este Id não existe!!!!");
            }

            ReportsDetailsDTO reportFuncionarioDTO = new ReportsDetailsDTO
            {
                Tipo = reportFuncionario.Tipo.ToString(),
                Titulo = reportFuncionario.Titulo,
                Descricao = reportFuncionario.Descricao,
                Cliente = reportFuncionario.Reporter.Nome,
                Funcionario = reportFuncionario.Reported.Nome
            };

            return reportFuncionarioDTO;


        }

        /// <summary>
        /// Metodo para realizar um report do Funcionario do Chat
        /// </summary>
        /// <param name="reportFuncionario"></param>
        /// <returns></returns>
        // POST: api/ReportFuncionarios/Chat
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("Chat")]
        //[Authorize("Cliente")]
        public async Task<ActionResult<ReportFuncionario>> PostReportFuncionarioChat(ReportDTOFuncionario reportFuncionario)
        {
            var foundFuncionario = await _context.Funcionarios.FindAsync(reportFuncionario.Reported);
            var foundCliente = await _context.Clientes.FindAsync(reportFuncionario.Reporter);

            if(foundCliente == null)
            {
                return NotFound("Este ReportCliente não existe!!!");
            }

            if (foundFuncionario == null)
            {
                return NotFound("Este ReportFuncionário não existe!!!");
            }

            var reportFuncionarioTemp = new ReportFuncionario
            {
                Titulo = reportFuncionario.Titulo,
                Descricao = reportFuncionario.Descricao,
                Tipo = TipoReport.Chat,
                Reported = foundFuncionario,
                Reporter = foundCliente,
                Arquivado = false
            };

            _context.ReportsFuncionario.Add(reportFuncionarioTemp);

            await _context.SaveChangesAsync();

            return reportFuncionarioTemp;
        }

        /// <summary>
        /// Metodo para realizar um report do Funcionario do Servico
        /// </summary>
        /// <param name="reportFuncionario"></param>
        /// <returns></returns>
        // POST: api/ReportFuncionarios/Servico
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("Servico")]
        //[Authorize("Cliente")]
        public async Task<ActionResult<ReportFuncionario>> PostReportFuncionarioServico(ReportDTOFuncionario reportFuncionario)
        {
            var foundFuncionario = await _context.Funcionarios.FindAsync(reportFuncionario.Reported);
            var foundCliente = await _context.Clientes.FindAsync(reportFuncionario.Reporter);


            if(foundFuncionario == null)
            {
                return NotFound("Este Cliente não existe!!");
            }

            if(foundCliente == null)
            {
                return NotFound("Este Funcionário não existe!!!");
            }
            var reportFuncionarioTemp = new ReportFuncionario
            {
                Titulo = reportFuncionario.Titulo,
                Descricao = reportFuncionario.Descricao,
                Tipo = TipoReport.Servico,
                Reported = foundFuncionario,
                Reporter = foundCliente,
                Arquivado = false
            };

            _context.ReportsFuncionario.Add(reportFuncionarioTemp);

            await _context.SaveChangesAsync();

            return reportFuncionarioTemp;
        }

        /// <summary>
        /// Metodo que verifica a existencia de um report
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        private bool ReportFuncionarioExists(int id)
        {
            return _context.ReportsFuncionario.Any(e => e.Id == id);
        }

      
    }
}
