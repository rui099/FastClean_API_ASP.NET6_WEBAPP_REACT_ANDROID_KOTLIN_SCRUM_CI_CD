using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using FastCleanAPI.Models;
using FastCleanAPI.DTO_s.ChatDTO;
using Microsoft.AspNetCore.Authorization;

namespace FastCleanAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ChatsController : ControllerBase
    {
        private readonly FastCleanContext _context;

        /// <summary>
        /// Metodo Construtor
        /// </summary>
        /// <param name="context">DBset das tabelas que estão nas base de dados</param>        
        public ChatsController(FastCleanContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Metodo que retorna o Chats
        /// </summary>
        /// <returns></returns>
        // GET: api/Chats
        [HttpGet]
        [AllowAnonymous]
        public async Task<ActionResult<IEnumerable<Chat>>> GetChats()
        {
            return await _context.Chats.ToListAsync();
        }

        /// <summary>
        /// Metodo para retornar um determinado Chat
        /// </summary>
        /// <param name="id">Id do chat que queremos retornar</param>
        /// <returns>NotFound quando o Chat não exisitir</returns>
        /// <returns>Ok quando existe o Chat e é retornado</returns>
        // GET: api/Chats/5
        [HttpGet("{id}")]
        [AllowAnonymous]
        public async Task<ActionResult<ChatDetailsDTO>> GetChat(int id)
        {
            var chat = await _context.Chats.FindAsync(id);

            if (chat == null)
            {
                return NotFound("Este Chat não existe!!");
            }

            List<ChatMensagem> messages = new List<ChatMensagem>();

            foreach(var c in chat.ListaMensagens)
            {  
                messages.Add(new ChatMensagem(c.Sender.Nome,c.Text));
            }

            ChatDetailsDTO details = new ChatDetailsDTO
            {
                id = id,
                idCliente = chat.Cliente.Id,
                Cliente = chat.Cliente.Nome,
                imageCliente = chat.Cliente.Imagem,
                Funcionario = chat.Funcionario.Nome,
                imageFuncionario = chat.Funcionario.Imagem,
                idFuncionario = chat.Funcionario.Id,
                Mensagens = messages
            };

            return Ok(details);
        }


        /// <summary>
        /// Metodo para remover Chat no fim da marcação!!!
        /// </summary>
        /// <param name="idCliente">Id do Cliente que esta no Chat</param>
        /// <param name="idFuncionario">Id do Funcionário que esta no Chat</param>
        /// <returns>Ok, caso o Chat seja removido!!!</returns>
        [HttpDelete("removerChat/{idCliente}/{idFuncionario}")]
        public async Task<IActionResult> removerChat(int idCliente, int idFuncionario)
        {
            Cliente cli = await _context.Clientes.FindAsync(idCliente);
            Funcionario func = await _context.Funcionarios.FindAsync(idFuncionario);


            if (func == null)
            {
                return NotFound("Este funcionário não existe!!!");
            }

            if(cli == null)
            {
                return NotFound("Este Cliente não existe!!!");
            }

            var chats = await _context.Chats.ToListAsync();

            Chat chatTemp = new Chat();

            foreach(var chat in chats)
            {
                if (chat.Cliente.Id == cli.Id && chat.Funcionario.Id == func.Id)
                {
                    chatTemp = chat;
                }
            }


            _context.Chats.Remove(chatTemp);
            await _context.SaveChangesAsync();

            return Ok("Este Chat foi removido!!!");
        }

        /// <summary>
        /// Metodo para retornar as mensagens de um Chat
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        // GET: api/Chats/Mensagens/5
        [HttpGet("Mensagens/{id}")]
        [AllowAnonymous]
        public async Task<ActionResult<List<ChatMensagem>>> GetChatMensagens(int id)
        {
            var chat = await _context.Chats.FindAsync(id);

            if (chat == null)
            {
                return NotFound("Não Chat existe.");
            }

            List<ChatMensagem> chatMensagems = new List<ChatMensagem>();


            foreach(var c in chat.ListaMensagens)
            {
                chatMensagems.Add(new ChatMensagem(c.Sender.Nome, c.Text));
            }

            return chatMensagems;
        }

       
        /// <summary>
        /// Metodo que cria um Chat
        /// </summary>
        /// <param name="chat">DTO do Chat</param>
        /// <returns></returns>
        // POST: api/Chats
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Chat>> PostChat(ChatDTO chat)
        {
            var foundCliente = await _context.Clientes.FindAsync(chat.Cliente);
            var foundFuncionario = await _context.Funcionarios.FindAsync(chat.Funcionario);


            if(foundCliente == null)
            {
                return NotFound("Este Cliente não existe!");
            }

            if(foundFuncionario == null)
            {
                return NotFound("Este Funcionário não existe!!");
            }

            //var marcacoes = _context.Marcacoes.ToList();

            //foreach(var marcacao in marcacoes)
            //{
            //    if(marcacao.Terminada==true && (marcacao.Funcionario.Id == foundFuncionario.Id && marcacao.Cliente.Id == foundCliente.Id))
            //    {
            //        return BadRequest("Não pode ter Chat entre ambos porque não têm marcação;");
            //    }
            //}


            Chat chatTemp = new Chat
            {
                Cliente = foundCliente,
                Funcionario = foundFuncionario
            };

            _context.Chats.Add(chatTemp);
            await _context.SaveChangesAsync();

            return Ok(chatTemp);
        }

        
       
       
    }
}
