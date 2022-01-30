using FastCleanAPI.Models;
using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.Utilizador
{
    public abstract class UtilizadorRegister
    {  
        public int Id { get; set; }

        [Required]
        public string Nome  { get; set; }

        [Required]
        public string Password  { get; set; }

        [Required]
        public Cargos Cargo {  get; set; }  

        [Required]
        [DataType(DataType.Date)]
        [DisplayFormat(DataFormatString = "{0:dd/MM/yyyy}")]
        public DateTime Idade { get; set; }

        [Required]
        public string Email { get; set; }

        public Morada Morada { get; set; }      
        
        [Required]
        public int Contacto { get; set; }

        public IFormFile Ccfile { get; set; }  

        public IFormFile Imagem { get; set; }

        public IFormFile Cadastro { get; set; }

        public virtual List<Models.Marcacao> ListaDeMarcacoes { get; set; }

        public virtual List<Chat> ListaDeChats { get; set; }

        public UtilizadorRegister()
        {
            ListaDeMarcacoes = new List<Models.Marcacao>();
            ListaDeChats = new List<Chat>();
        }

    }
}
