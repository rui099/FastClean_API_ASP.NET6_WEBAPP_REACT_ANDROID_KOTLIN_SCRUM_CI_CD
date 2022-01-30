namespace FastCleanAPI.Models
{
    public class Mensagem
    {
        public int Id { get; set; }
        public virtual Utilizador Sender  { get; set; }
        public string Text { get; set; }
        public virtual Chat Chat { get; set; }
    }
}
