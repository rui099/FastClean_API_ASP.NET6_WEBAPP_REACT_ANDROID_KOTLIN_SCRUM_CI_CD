using FastCleanAPI.Models;
using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.Reports
{
    public abstract class ReportDTO
    {
        [Required]
        public int Id { get; set; }

        [Required]
        public string Titulo { get; set; }

        [Required]
        public string Descricao { get; set; }

        public TipoReport Tipo { get; set; }

    }
}
