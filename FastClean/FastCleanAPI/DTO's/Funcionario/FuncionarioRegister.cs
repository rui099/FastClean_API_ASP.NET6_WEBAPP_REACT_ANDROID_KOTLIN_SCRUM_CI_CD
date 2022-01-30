using FastCleanAPI.DTO_s.Utilizador;
using FastCleanAPI.Models;
using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.Funcionario
{
    public class FuncionarioRegister : UtilizadorRegister
    {
        [Required]
        public double Preco { get; set; }
        public IFormFile CartaDeConducao { get; set; } 

        public TipoLimpeza TipoLimpeza { get; set; }
        
        public IFormFile HistoricoMedico { get; set; }

        public IFormFile CvFile { get; set; }

        
        public bool Subscricao { get; set; }    

        public virtual List<ReportFuncionario> ListaDeReports { get; set; }

        public virtual List<ReviewFuncionario> ListaDeReviews { get; set;}


        public FuncionarioRegister()
        {
             ListaDeReports = new List<ReportFuncionario>();
             ListaDeReviews = new List<ReviewFuncionario>();
        }
    }
}
