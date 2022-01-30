namespace FastCleanAPI.DTO_s.ChatDTO
{
    public class ChatMensagem
    {

        public string nomeSender { get; set; }

        public string Mensagem { get; set; }


        public ChatMensagem( string sender, string text)
        {
            this.nomeSender = sender;
            this.Mensagem = text;
        }
    }
}
