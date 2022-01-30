using FastCleanAPI.Models;
using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.Utilizador
{
    public class UtilizadorDetails
    {
        public int Id { get; set; }

        [Required]
        public string Nome { get; set; }
        public Byte[] Imagem { get; set; }
        public Morada Morada { get; set; }

        public int Contacto { get; set; }

        public string Cadastro { get; set; }

        public string HistoricoMedico { get; set; }

        public string CvFile { get; set; }

        public string CartaDeConducao { get; set; }

        public string CcFile { get; set; }

        public int Idade { get; set; }
        [Required]
        public string Email { get; set; }

        [Required]
        public string TipoUtilizador { get; set; }

        public UtilizadorDetails() { }
    }
}
