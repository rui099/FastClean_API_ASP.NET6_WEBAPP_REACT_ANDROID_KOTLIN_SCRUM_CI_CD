using FastCleanAPI.Models;
using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.ChatDTO
{
    public class ChatDTO
    {
        public int Id { get; set; }


        [Required]
        public int Cliente { get; set; }


        [Required]
        public int Funcionario { get; set; }

        public virtual List<Mensagem> Mensagens { get; set; }


        public ChatDTO()
        {
            Mensagens = new List<Mensagem>();
        }
    }
}
