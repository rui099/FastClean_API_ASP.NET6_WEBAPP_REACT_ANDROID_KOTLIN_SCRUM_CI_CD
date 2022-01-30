using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using FastCleanAPI.Models;
using FastCleanAPI.DTO_s.ReviewFuncionarioDTO;
using Microsoft.AspNetCore.Authorization;

namespace FastCleanAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ReviewFuncionariosController : ControllerBase
    {
        private readonly FastCleanContext _context;

        /// <summary>
        /// Metodo Construtor
        /// </summary>
        /// <param name="context"></param>
        public ReviewFuncionariosController(FastCleanContext context)
        {
            _context = context;
        }

       
        /// <summary>
        /// Metodo que retorna um review de Funcionário
        /// </summary>
        /// <param name="id">ID da review do Funcionário</param>
        /// <returns>os detalhes das reviews</returns>
        // GET: api/ReviewFuncionarios/5
        [HttpGet("{id}")]
        ///[Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<FuncionarioReviewDetails>> GetReviewFuncionario(int id)
        {
            var reviewFuncionario = await _context.ReviewsFuncionarios.FindAsync(id);

            if (reviewFuncionario == null)
            {
                return NotFound("Esta Review não existe!!");
            }

            return new FuncionarioReviewDetails
            {

                Id = reviewFuncionario.Id,
                Comentario = reviewFuncionario.Comentario,
                Cotacao = reviewFuncionario.Cotacao,
                Data = reviewFuncionario.Data,
                ReviewedID = reviewFuncionario.Reviewed.Id,
                ReviewedName = reviewFuncionario.Reviewed.Nome,
                ReviewerID = reviewFuncionario.Reviewer.Id,
                ReviewerName = reviewFuncionario.Reviewer.Nome
            };
        }

        /// <summary>
        /// Metodo para fazer uma review de um Funcionário
        /// </summary>
        /// <param name="reviewFuncionario"></param>
        /// <returns></returns>
        // POST: api/ReviewFuncionarios
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        //[Authorize("Cliente")]
        public async Task<ActionResult<FuncReviewDTO>> PostReviewFuncionario(FuncReviewDTO reviewFuncionario)
        {
            var foundCliente = await _context.Clientes.FindAsync(reviewFuncionario.Reviewer);
            var foundFuncionario = await _context.Funcionarios.FindAsync(reviewFuncionario.Reviewed);

            if(foundCliente == null)
            {
                return NotFound("Este Cliente não existe!!!");
            }

            if(foundFuncionario == null)
            {
                return NotFound("Este Funcionário não existe!!!");  
            }

            DateTime data = DateTime.Now;
            string aux = String.Format("{0:dd/MM/yyyy}", data);

            var review = new ReviewFuncionario
            {
                Comentario = reviewFuncionario.Comentario,
                Cotacao = reviewFuncionario.Cotacao,
                Data = aux,
                Reviewed = foundFuncionario,
                Reviewer = foundCliente
            };
            _context.ReviewsFuncionarios.Add(review);

            await _context.SaveChangesAsync();


            return Ok(review);
        }


        /// <summary>
        /// Metodo para retornar media das reviews
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        // GET: api/ReviewFuncionarios/UserReviews/5
        [HttpGet("UserReviewsMedia/{id}")]
        ///[Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<ReviewFuncionarioListMedia>> getReviews(int id)
        {
            var funcionario = await _context.Funcionarios.FindAsync(id);

            if (funcionario == null)
            {
                return NotFound("Este Funcionario não existe!!");
            }


            ReviewFuncionarioListMedia mediaELista = new ReviewFuncionarioListMedia();

            int count = 0;
            double sum = 0;

            foreach (var review in funcionario.ListaDeReviews)
            {
                    count++;
                    sum = sum + review.Cotacao;

                    mediaELista.ListaReviews.Add(new FuncionarioReviewDetails { 
                    
                        Id= review.Id,
                        Comentario =review.Comentario,
                        Cotacao = review.Cotacao,
                        ReviewedID = review.Reviewed.Id,
                        ReviewedName = review.Reviewed.Nome,
                        ReviewerID = review.Reviewer.Id,
                        ReviewerName = review.Reviewer.Nome,
                        Data = review.Data
                    });
            }


            if (count != 0)
                mediaELista.Media = Math.Round(sum / count);
            else
                mediaELista.Media = 0;

            return mediaELista;
        }

        /// <summary>
        /// Metodo para fazer a review dos ultimos 30 dias
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        // GET: api/ReviewClientes/media30d/5
        [HttpGet("media30d/{id}")]
        ///[Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<ReviewFuncionarioListMedia>> getReviews30(int id)
        {
            var funcionario = await _context.Funcionarios.FindAsync(id);
            if (funcionario == null)
            {
                return NotFound("Este Funcionário não existe!!");
            }

            
            ReviewFuncionarioListMedia mediaELista = new ReviewFuncionarioListMedia();
            DateTime currentDate = DateTime.Now;
            DateTime less30Date = currentDate.AddDays(-30);
            int count = 0;
            double sum = 0;

            foreach (var review in funcionario.ListaDeReviews)
            {
                if (DateTime.Parse(review.Data) > less30Date)
                {
                    mediaELista.ListaReviews.Add(new FuncionarioReviewDetails
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
                    count++;
                    sum = sum + review.Cotacao;
                }
            }
            if (count != 0)
                mediaELista.Media = Math.Round(sum / count);
            else
                mediaELista.Media = 0;

            return mediaELista;
        }

        /// <summary>
        /// Metodo para fazer a media das reviews dos ultimos 15 dias
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpGet("media15d/{id}")]
        ///[Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<ReviewFuncionarioListMedia>> getReviews15(int id)
        {
            var funcionario = await _context.Funcionarios.FindAsync(id);
            if (funcionario == null)
            {
                return NotFound("Este Funcionário não existe!!!");
            }

            
            ReviewFuncionarioListMedia mediaELista = new ReviewFuncionarioListMedia();
            DateTime currentDate = DateTime.Now;
            DateTime less15Date = currentDate.AddDays(-15);
            int count = 0;
            double sum = 0;

            foreach (var review in funcionario.ListaDeReviews)
            {
                if (DateTime.Parse(review.Data) > less15Date)
                {
                    mediaELista.ListaReviews.Add(new FuncionarioReviewDetails
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
                    count++;
                    sum = sum + review.Cotacao;
                }
            }

            if (count != 0)
                mediaELista.Media = Math.Round(sum / count);
            else
                mediaELista.Media = 0;

            return mediaELista;
        }

        /// <summary>
        /// Metodo para fazer as medias das reviews de um semestre
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpGet("mediaSemestre/{id}")]
        ///[Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<ReviewFuncionarioListMedia>> getReviews180(int id)
        {
            var funcionario = await _context.Funcionarios.FindAsync(id);
            if (funcionario == null)
            {
                return NotFound();
            }

            List<ReviewFuncionario> lista = funcionario.ListaDeReviews;
            ReviewFuncionarioListMedia mediaELista = new ReviewFuncionarioListMedia();
            DateTime currentDate = DateTime.Now;
            DateTime less180Date = currentDate.AddDays(-180);
            int count = 0;
            double sum = 0;

            foreach (var review in funcionario.ListaDeReviews)
            {
                if (DateTime.Parse(review.Data) > less180Date)
                {
                    mediaELista.ListaReviews.Add(new FuncionarioReviewDetails
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
                    count++;
                    sum = sum + review.Cotacao;
                }
            }
            if (count != 0)
                mediaELista.Media = Math.Round(sum / count);
            else
                mediaELista.Media = 0;

            return mediaELista;
        }


        /// <summary>
        /// Metodo para realizar a media das reviews do no ultimo trimestre
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpGet("mediaTrimestre/{id}")]
        ///[Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<ReviewFuncionarioListMedia>> getReviews90(int id)
        {
            var funcionario = await _context.Funcionarios.FindAsync(id);

            if (funcionario == null)
            {
                return NotFound("Este Funcionário não existe!!");
            }

            
            ReviewFuncionarioListMedia mediaELista = new ReviewFuncionarioListMedia();
            DateTime currentDate = DateTime.Now;
            DateTime less90Date = currentDate.AddDays(-90);
            int count = 0;
            double sum = 0;

            foreach (var review in funcionario.ListaDeReviews)
            {
                if (DateTime.Parse(review.Data) > less90Date)
                {
                    mediaELista.ListaReviews.Add(new FuncionarioReviewDetails
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
                    count++;
                    sum = sum + review.Cotacao;
                }
            }

            if (count != 0)
                mediaELista.Media = Math.Round(sum / count);
            else
                mediaELista.Media = 0;

            return mediaELista;
        }

       
       
    }
}
