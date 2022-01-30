namespace FastCleanAPI.Models
{
    public class Chat
    {
        public int Id { get; set; }
        public virtual List<Mensagem>? ListaMensagens { get; set;}
        public virtual Cliente Cliente { get; set; }
        public virtual Funcionario Funcionario { get; set; }

        public Chat()
        {
            ListaMensagens = new List<Mensagem>();
        }
    }
}
