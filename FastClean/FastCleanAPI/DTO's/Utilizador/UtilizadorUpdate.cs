using FastCleanAPI.Models;
using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.Utilizador
{
    public abstract class UtilizadorUpdate
    {   
        public int Id { get; set; }

        
        public IFormFile Imagem { get; set; }  

        
        public Morada Morada { get; set; }  

        
        public int Contacto { get; set; }  
        
        public string Email { get; set; }   
        
        public IFormFile CcFile { get; set; }

        
        public IFormFile Cadastro { get; set; }
   

    }
}
