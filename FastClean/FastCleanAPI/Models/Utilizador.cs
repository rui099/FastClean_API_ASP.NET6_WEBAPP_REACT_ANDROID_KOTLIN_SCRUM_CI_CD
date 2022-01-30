namespace FastCleanAPI.Models
{
    public class Utilizador 
    {
        public int Id { get; set; }
        public Boolean Banido { get; set; }
        public bool Aceite { get; set; }
        public string Nome { get; set; }
        public string Password { get; set; }
        public int Idade { get; set; } 
        public Cargos Cargo { get; set; }
        public string Email { get; set; }
        public int Contacto { get; set; }
        public virtual Morada Morada { get; set; }
        public string CcFile { get; set; }
        public string Imagem { get; set; }
        public string Cadastro { get; set; }
        public string? Latitude { get; set; }
        public string? Longitude { get; set; }
        public virtual List<Marcacao>? ListaDeMarcacoes { get; set; }
        public virtual List<Chat>? ListaDeChats { get; set; }

        public Utilizador()
        {
            ListaDeMarcacoes = new List<Marcacao>();
            ListaDeChats = new List<Chat>();
        }
    }
}
