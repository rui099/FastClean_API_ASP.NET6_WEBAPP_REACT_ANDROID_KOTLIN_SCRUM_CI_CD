using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using FastCleanAPI.Models;
using FastCleanAPI.DTO_s.ReviewClienteDTO;
using Microsoft.AspNetCore.Authorization;

namespace FastCleanAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ReviewClientesController : ControllerBase
    {
        private readonly FastCleanContext _context;

        /// <summary>
        /// Metodo Construtor
        /// </summary>
        /// <param name="context"></param>
        public ReviewClientesController(FastCleanContext context)
        {
            _context = context;
        }

     
        /// <summary>
        /// Metodo para retornar uma review de um cliente
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        // GET: api/ReviewClientes/5
        [HttpGet("{id}")]
        //[Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<ReviewClienteDetails>> GetReviewCliente(int id)
        {
            var reviewCliente = await _context.ReviewsClientes.FindAsync(id);

            if (reviewCliente == null)
            {
                return NotFound("Esta review não existe!!!");
            }

            return new ReviewClienteDetails
            { 
             
                Id =reviewCliente.Id,
                Comentario = reviewCliente.Comentario,
                Cotacao = reviewCliente.Cotacao,
                Data = reviewCliente.Data,
                ReviewedID = reviewCliente.Reviewed.Id,
                ReviewedName = reviewCliente.Reviewed.Nome,
                ReviewerID = reviewCliente.Reviewer.Id,
                ReviewerName = reviewCliente.Reviewer.Nome
            };
        }

       

        /// <summary>
        /// Metodo para fazer uma review a um Cliente
        /// </summary>
        /// <param name="reviewCliente"></param>
        /// <returns></returns>
        // POST: api/ReviewClientes
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        //[Authorize("Funcionario")]
        public async Task<ActionResult<ClienteReviewDTO>> PostReviewCliente(ClienteReviewDTO reviewCliente)
        {
            var foundCliente = await _context.Clientes.FindAsync(reviewCliente.Reviewed);
            var foundFuncionario = await _context.Funcionarios.FindAsync(reviewCliente.Reviewer);


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

            var review = new ReviewCliente
            {
                Comentario = reviewCliente.Comentario,
                Cotacao = reviewCliente.Cotacao,
                Data = aux,
                Reviewed = foundCliente,
                Reviewer = foundFuncionario,
            };
            _context.ReviewsClientes.Add(review);

            await _context.SaveChangesAsync();

            return Ok(review);
        }

       

        /// <summary>
        /// Metodo para retornar a media das reviews
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        // GET: api/ReviewClientes/5
        [HttpGet("UserReviewsMedia/{id}")]
        //[Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<ClienteReviewListMedia>> getReviews(int id)
        {
            var cliente = await _context.Clientes.FindAsync(id);

            if (cliente == null)
            {
                return NotFound("Este Cliente não existe!!");
            }

            
            ClienteReviewListMedia mediaELista = new ClienteReviewListMedia();

            int count = 0;
            double sum = 0;

            foreach (var review in cliente.ListaDeReviews)
            {
                    count++;
                    sum = sum + review.Cotacao;

                    mediaELista.ListaReviews.Add(new ReviewClienteDetails { 
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


            if (count != 0)
                mediaELista.Media = Math.Round(sum / count);
            else
                mediaELista.Media = 0;

            return mediaELista;
        }

        /// <summary>
        /// Metodo para retornar a medias das reviews dos ultimos 30 dias
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        // GET: api/ReviewClientes/media30d/5
        [HttpGet("media30d/{id}")]
        [Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<ClienteReviewListMedia>> getReviews30(int id)
        {
            var cliente = await _context.Clientes.FindAsync(id);

            if (cliente == null)
            {
                return NotFound("Este Cliente não existe!!");
            }

            ClienteReviewListMedia mediaELista = new ClienteReviewListMedia();

            DateTime currentDate = DateTime.Now;
            DateTime less30Date = currentDate.AddDays(-30);
            int count = 0;
            double sum = 0;

            foreach (var review in cliente.ListaDeReviews)
            {
                if (DateTime.Parse(review.Data) > less30Date)
                {
                    mediaELista.ListaReviews.Add(new ReviewClienteDetails
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
        /// Metodo para retornar a medias das reviews dos ultimos 15 dias 
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpGet("media15d/{id}")]
        [Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<ClienteReviewListMedia>> getReviews15(int id)
        {
            var cliente = await _context.Clientes.FindAsync(id);

            if (cliente == null)
            {
                return NotFound("Este cliente não existe!!");
            }

            ClienteReviewListMedia mediaELista = new ClienteReviewListMedia();

            DateTime currentDate = DateTime.Now;
            DateTime less15Date = currentDate.AddDays(-15);
            int count = 0;
            double sum = 0;

            foreach (var review in cliente.ListaDeReviews)
            {
                if (DateTime.Parse(review.Data) > less15Date)
                {
                    mediaELista.ListaReviews.Add(new ReviewClienteDetails
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
        /// Metodo para retornar as medias do ultimo semestre
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpGet("mediaSemestre/{id}")]
        [Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<ClienteReviewListMedia>> getReviews180(int id)
        {
            var cliente = await _context.Clientes.FindAsync(id);
            if (cliente == null)
            {
                return NotFound("Este Cliente não existe!!");
            }

            
            ClienteReviewListMedia mediaELista = new ClienteReviewListMedia();
            DateTime currentDate = DateTime.Now;
            DateTime less180Date = currentDate.AddDays(-180);
            int count = 0;
            double sum = 0;

            foreach (var review in cliente.ListaDeReviews)
            {
                if (DateTime.Parse(review.Data) > less180Date)
                {
                    mediaELista.ListaReviews.Add(new ReviewClienteDetails
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
        /// Metodo para retornar as medias das reviews do ultimo trimestre
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpGet("mediaTrimestre/{id}")]
        [Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<ClienteReviewListMedia>> getReviews90(int id)
        {
            var cliente = await _context.Clientes.FindAsync(id);
            if (cliente == null)
            {
                return NotFound("Este Cliente não existe!!!");
            }

            
            ClienteReviewListMedia mediaELista = new ClienteReviewListMedia();

            DateTime currentDate = DateTime.Now;
            DateTime less90Date = currentDate.AddDays(-90);
            int count = 0;
            double sum = 0;

            foreach (var review in cliente.ListaDeReviews)
            {
                if (DateTime.Parse(review.Data) > less90Date)
                {
                    mediaELista.ListaReviews.Add(new ReviewClienteDetails
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
