#nullable disable
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using FastCleanAPI.Models;
using FastCleanAPI.DTO_s.ChatDTO;

namespace FastCleanAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class MensagemsController : ControllerBase
    {
        private readonly FastCleanContext _context;

        /// <summary>
        /// Metodo construtor
        /// </summary>
        /// <param name="context">DBset das tabelas que estão nas base de dados</param>
        public MensagemsController(FastCleanContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Metodo que retorna todas as mensagens existentes
        /// </summary>
        /// <returns>Todas as mensagens</returns>
        // GET: api/Mensagems
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Mensagem>>> GetMensagens()
        {
            return await _context.Mensagens.ToListAsync();
        }

        /// <summary>
        /// Este metodo vai retornar uma mensagem 
        /// </summary>
        /// <param name="id">Id da mensagem que queremos obter informação</param>
        /// <returns>NotFound caso a mensagem que queremos retornar não exista</returns>
        /// <returns>Mensagem que procuramos</returns>
        // GET: api/Mensagems/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ChatMensagem>> GetMensagem(int id)
        {
            var mensagem = await _context.Mensagens.FindAsync(id);

            if (mensagem == null)
            {
                return NotFound("Esta mensagem não existe!!!");
            }

            ChatMensagem mensages = new ChatMensagem(mensagem.Sender.Nome,
                mensagem.Text);
            

            return mensages;
        }

        /// <summary>
        /// Metodo que possibilitar enviar mensagens
        /// </summary>
        /// <param name="idChat">ID do chat em que queremos enviar a mensagem</param>
        /// <param name="idSender">Quem vai enviar a mensagem</param>
        /// <param name="mensagem">O DTO para o envio da Mensagem</param>
        /// <returns>NotFound caso o Utilizador não exista</returns>
        /// <returns>NotFound caso o Chat não exista</returns>
        /// <returns>NotFound caso o sender não seja nem o Funcionario nem o Cliente que esta no Chat</returns>
        /// <returns>Ok caso a mensagem seja enviada</returns>
        // POST: api/Mensagems
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost("{idChat}/{idSender}")]
        public async Task<ActionResult<Mensagem>> PostMensagem(int idChat, int idSender,SendMensagemDTO mensagem)
        {
            
            Utilizador user = await _context.Utilizador.FindAsync(idSender);

            if (user == null)
            {
                return NotFound("Este Utilizador não existe.");
            }

            Chat chatID = await _context.Chats.FindAsync(idChat);

            if (chatID == null)
            {
                return NotFound("Este Chat não existe.");
            }

            Mensagem mensagemTemp = new Mensagem
            {
                Sender = user,
                Text = mensagem.Text,
                Chat = chatID
            };

            _context.Mensagens.Add(mensagemTemp);
            await _context.SaveChangesAsync();

            return Ok(mensagemTemp);
        }

        /// <summary>
        /// Apagar uma mensagem
        /// </summary>
        /// <param name="id">Id da mensagem que queremos eliminar</param>
        /// <returns>NotFound caso a mensagem não exista</returns>
        // DELETE: api/Mensagems/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteMensagem(int id)
        {
            var mensagem = await _context.Mensagens.FindAsync(id);
            if (mensagem == null)
            {
                return NotFound("Esta mensagem não existe");
            }

            _context.Mensagens.Remove(mensagem);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        /// <summary>
        /// Metodo que verifica se a mensagem existe
        /// </summary>
        /// <param name="id">Id da mensagem que procuramos</param>
        /// <returns>True se a mensagem existir</returns>
        /// <returns>False se a mensagem não existir</returns>
        private bool MensagemExists(int id)
        {
            return _context.Mensagens.Any(e => e.Id == id);
        }
    }
}
