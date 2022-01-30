using FastCleanAPI.DTO_s.Utilizador;
using FastCleanAPI.Models;
using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.Funcionario
{
    public class FuncionarioUpdate : UtilizadorUpdate
    {
        
        public IFormFile CartaDeConducao { get; set; }

        
        public IFormFile HistoricoMedico { get; set; }

        
        public IFormFile CvFile  { get; set; }


        public double Preco { get; set; }

        public TipoLimpeza TipoLimpeza { get; set; } 


    }
}
