using FastCleanAPI.Models;
using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.Utilizador
{
    public  class ImageDTO
    {   
        public IFormFile Imagem { get; set; }  
    }
}
