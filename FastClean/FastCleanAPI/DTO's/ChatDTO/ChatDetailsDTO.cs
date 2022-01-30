using FastCleanAPI.Models;

namespace FastCleanAPI.DTO_s.ChatDTO
{
    public class ChatDetailsDTO
    {
        public int id { get; set; }
        public int idCliente { get; set; }
        public string Cliente { get; set; }
        public string imageCliente { get; set; }
        public string Funcionario { get; set; }
        public string imageFuncionario { get; set;}
        public int idFuncionario { get; set; }
        public virtual List<ChatMensagem> Mensagens { get; set; }


        public ChatDetailsDTO()
        {
            Mensagens = new List<ChatMensagem>();
        }
    }
}
