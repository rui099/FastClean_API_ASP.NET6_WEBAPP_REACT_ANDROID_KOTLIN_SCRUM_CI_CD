using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using FastCleanAPI.Models;
using FastCleanAPI.DTO_s.ReportsDTO;
using Microsoft.AspNetCore.Authorization;

namespace FastCleanAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ReportsController : ControllerBase
    {
        private readonly FastCleanContext _context;
        
        /// <summary>
        /// Metodo Construtor
        /// </summary>
        /// <param name="context"></param>
        public ReportsController(FastCleanContext context)
        {
            _context = context;
  
        }

        /// <summary>
        /// Metodo para retornar todos os reports
        /// </summary>
        /// <returns></returns>
        //GET: api/Reports
        [HttpGet]
        //[Authorize("Administrador")]
        public async Task<ActionResult<IEnumerable<Report>>> GetAllReport()
        {
            var reportsClientes = await _context.ReportsClientes.ToListAsync();
            var reportsFuncionarios = await _context.ReportsFuncionario.ToListAsync();
            var listaDeReports = new List<Report>();

            foreach (var report in reportsClientes)
            {
                listaDeReports.Add(report);
            }

            foreach (var report in reportsFuncionarios)
            {
                listaDeReports.Add(report);
            }

            //List<ReportsDetailsDTO> listAllReports = new List<ReportsDetailsDTO>();
            //foreach(var report in listaDeReports)
            //{
            //    listAllReports.Add(new ReportsDetailsDTO
            //    {
            //        Tipo = report.Tipo.ToString(),
            //        Titulo = report.Titulo,
            //        Descricao = report.Descricao
            //    });
            //}

            return listaDeReports;
        }

        
        /// <summary>
        /// Metodo para retornar todos os reports do Chat
        /// </summary>
        /// <returns>Not Found caso não exista reports do Tipo Chat</returns>
        /// <returns>Lista de Reports de Chats</returns>
        // GET: api/Reports/TipoChat
        [HttpGet("TipoChat/{Chat}")]
        [Authorize("Administrador")]
        public async Task<ActionResult<IEnumerable<Report>>> GetReportForChat()
        {
            // Passos para juntar todos os reports numa lista
            var reportsClientes = await _context.ReportsClientes.ToListAsync();
            var reportsFuncionarios = await _context.ReportsFuncionario.ToListAsync();
            var listaDeReports = new List<Report>();

            foreach (var report in reportsClientes)
            {
                listaDeReports.Add(report);
            }

            foreach (var report in reportsFuncionarios)
            {
                listaDeReports.Add(report);
            }

            List<Report> listaReportsChat = new List<Report>();

            //procurar na ListaReports e retornar outra lista só com reports do Chat
            foreach(var report in listaDeReports)
            {
                if(report.Tipo == TipoReport.Chat)
                {
                    listaReportsChat.Add(report);
                }
            }

            if(listaReportsChat.Count == 0)
            {
                return NotFound("Não existem reports do tipo Chat");
            }

            return listaReportsChat;
        }

        /// <summary>
        /// Metodo para retornar a lista de reports do serviço
        /// </summary>
        /// <returns>NotFound caso não existam reports do tipo Serviço</returns>
        /// <returns>A lista de reports do tipo serviço</returns>
        // GET: api/Reports/TipoServico/Servico
        [HttpGet("TipoServico/{Servico}")]
        [Authorize("Administrador")]
        public async Task<ActionResult<IEnumerable<Report>>> GetReportForServico()
        {
            // Passos para juntar todos os reports numa lista
            var reportsClientes = await _context.ReportsClientes.ToListAsync();
            var reportsFuncionarios = await _context.ReportsFuncionario.ToListAsync();
            var listaDeReports = new List<Report>();

            foreach (var report in reportsClientes)
            {
                listaDeReports.Add(report);
            }

            foreach (var report in reportsFuncionarios)
            {
                listaDeReports.Add(report);
            }
            List<Report> listaReportsServico = new List<Report>();

            // procurar na ListaReports e retornar outra lista só com reports do Servico
            foreach (var report in listaDeReports)
            {
                if (report.Tipo == TipoReport.Servico)
                {
                    listaReportsServico.Add(report);
                }
            }

            if (listaReportsServico.Count == 0)
            {
                NotFound("Não existem reports do tipo Servico");
            }
            return listaReportsServico;
        }

        /// <summary>
        /// Metodo retornar os reports feitos aos Funcionarios
        /// </summary>
        /// <returns>NotFound caso não existam reports de funcionários</returns>
        /// <returns>A lista de reports aos Funcionarios</returns>
        // GET: api/Reports/Funcionarios/ReportsFuncionarios
        [HttpGet("Funcionarios/ReportsFuncionarios")]
        [Authorize("Administrador")]
        public async Task<ActionResult<IEnumerable<ReportsDetailsDTO>>> GetReportAboutFuncionarios()
        {
            var reportsFuncionarios = await _context.ReportsFuncionario.ToListAsync();

            if (reportsFuncionarios.Count == 0)
            {
                return NotFound("Não existem reports de Funcionarios");
            }


            List<ReportsDetailsDTO> reportsDetailsDTOs = new List<ReportsDetailsDTO>();

            foreach (var reports in reportsFuncionarios)
            {
                if(reports.Arquivado != true)
                {
                    reportsDetailsDTOs.Add(new ReportsDetailsDTO
                    {
                        Titulo = reports.Titulo,
                        Descricao = reports.Descricao,
                        Tipo = reports.Tipo.ToString(),
                        Cliente = reports.Reporter.Nome,
                        Funcionario = reports.Reported.Nome
                    });
                }
                else
                {
                    return BadRequest("Não existem reports!!!");
                }
            };

            return reportsDetailsDTOs;
        }

        /// <summary>
        /// Metodo para retornar os reports dos Clientes
        /// </summary>
        /// <returns>NotFound caso não existam reports aos Clientes</returns>
        /// <returns>A Lista de reports aos Clientes</returns>
        // GET: api/Reports/ReportsClientes
        //[Authorize("Administrador")]
        [HttpGet("Clientes/ReportsClientes")]
        public async Task<ActionResult<List<ReportsDetailsDTO>>> GetReportAboutClientes()
        {

            List<ReportCliente> reportsClientes = await _context.ReportsClientes.ToListAsync();


            if (reportsClientes.Count == 0)
            {
                return NotFound("Não existem reports de Clientes!!!");
            }

            List<ReportsDetailsDTO> reportsDetailsDTOs = new List<ReportsDetailsDTO>();

            foreach (var reports in reportsClientes)
            {
                if(reports.Arquivado != true)
                {
                    reportsDetailsDTOs.Add(new ReportsDetailsDTO
                    {
                        Titulo = reports.Titulo,
                        Descricao = reports.Descricao,
                        Tipo = reports.Tipo.ToString(),
                        Cliente = reports.Reporter.Nome,
                        Funcionario = reports.Reported.Nome
                    });
                }
                else
                {
                    return BadRequest("Não existem reports!!!");
                }
            }

            return reportsDetailsDTOs;
        }

        /// <summary>
        /// Este metodo vai retornar os detalhes de um report Funcionario 
        /// </summary>
        /// <param name="id">Id do Report do Funcionário</param>
        /// <returns>NotFound caso o report não exista</returns>
        /// <returns>Report caso o report exista e será retornado os detalhes do report</returns>
        [HttpGet("ReportFuncionarioDetails/{id}")]
        public async Task<ActionResult<ReportsDetailsDTO>> GetDetailsReportFuncionario(int id)
        {

            var reportFuncionario = await _context.ReportsFuncionario.FindAsync(id);

            if(reportFuncionario == null)
            {
                return NotFound("Este report do Tipo Funcionário não existe.");
            }

            var report = new ReportsDetailsDTO
            {
                Titulo = reportFuncionario.Titulo,
                Descricao = reportFuncionario.Descricao,
                Tipo = reportFuncionario.Tipo.ToString(),
                Cliente =reportFuncionario.Reporter.Nome,
                Funcionario = reportFuncionario.Reported.Nome
            };

            return report;
        }


        /// <summary>
        /// Este metodo vai retornar os detalhes de um report Cliente 
        /// </summary>
        /// <param name="id">Id do Report do Cliente</param>
        /// <returns>NotFound caso o report não exista</returns>
        /// <returns>Report caso o report exista e será retornado os detalhes do reports</returns>
        [HttpGet("ReportClienteDetails/{id}")]
        public async Task<ActionResult<ReportsDetailsDTO>> GetDetailsReportCliente(int id)
        {

            var reportCliente = await _context.ReportsClientes.FindAsync(id);

            if (reportCliente == null)
            {
                return NotFound("Este report do Tipo Cliente não existe.");
            }

            var report = new ReportsDetailsDTO
            {
                Titulo = reportCliente.Titulo,
                Descricao = reportCliente.Descricao,
                Tipo = reportCliente.Tipo.ToString(),
                Cliente = reportCliente.Reported.Nome,
                Funcionario = reportCliente.Reporter.Nome
            };

            return report;
        }

        /// <summary>
        /// Este metodo vai arquivar  um report
        /// </summary>
        /// <param name="id">Id do Report que desejamos arquivar</param>
        /// <returns></returns>
        [HttpPut("Arquivar/{id}")]
        // PUT: api/Reports/Arquivar/id
        public async Task<Report> UpdateArquivo(int id)
        {
            
            List<Report> allReports = getAllReports();


            Report reportTemp = new Report();



            foreach (var re in allReports)
            {
                if (re.Id == id)
                {
                    reportTemp = re;
                }
            }


           if(reportTemp.Arquivado == false)
            {
                reportTemp.Arquivado = true;

                if (reportTemp is ReportCliente)
                {
                    try
                    {
                        await _context.SaveChangesAsync();
                    }
                    catch (DbUpdateConcurrencyException)
                    {

                    }
                }
                else
                {
                    try
                    {
                        await _context.SaveChangesAsync();
                    }
                    catch (DbUpdateConcurrencyException)
                    {

                    }
                }
                return reportTemp;
            }

            return null;

        }


        /// <summary>
        /// Este metodo vai retornar todos os reports tanto de Funcionario como de cliente
        /// </summary>
        /// <returns></returns>
        private List<Report> getAllReports()
        {

            var reportsClientes = _context.ReportsClientes.ToList();
            var reportsFuncionario = _context.ReportsFuncionario.ToList();

            List<Report> allReports = new List<Report>();
            foreach (var report in reportsClientes)
            {
                allReports.Add(report);
            }

            foreach (var report in reportsFuncionario)
            {
                allReports.Add(report);
            }

            return allReports;
        }


        /// <summary>
        /// Método para retornar todos os reports da Base de dados
        /// </summary>
        // GET: api/Reports/AllReports
        [HttpGet("AllReports")]
        //[Authorize("Administrador")]
        public async Task<ActionResult<List<ReportsDetailsDTO>>> GetAllReports()
        {
          

            List<Report> allReports = getAllReports();

            List<ReportsDetailsDTO> listaDeReports = new List<ReportsDetailsDTO>();

            foreach(var report in allReports)
            {

                if (report.Arquivado == false)
                {
                    if (report is ReportCliente)
                    {
                        var temp = (ReportCliente)report;
                        listaDeReports.Add(new ReportsDetailsDTO
                        {
                            Titulo = temp.Titulo,
                            Tipo = temp.Tipo.ToString(),
                            Descricao = temp.Descricao,
                            Cliente = temp.Reported.Nome,
                            Funcionario = temp.Reporter.Nome,
                            Arquivado = temp.Arquivado.ToString()
                        });
                    }
                    else if (report is ReportFuncionario)
                    {
                        var temp = (ReportFuncionario)report;
                        listaDeReports.Add(new ReportsDetailsDTO
                        {
                            Titulo = temp.Titulo,
                            Tipo = temp.Tipo.ToString(),
                            Descricao = temp.Descricao,
                            Cliente = temp.Reporter.Nome,
                            Funcionario = temp.Reported.Nome,
                            Arquivado = temp.Arquivado.ToString()
                        });
                    }
                }

            }

            return listaDeReports;
        }
    }

}
