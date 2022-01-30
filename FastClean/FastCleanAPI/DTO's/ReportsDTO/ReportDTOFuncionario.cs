using FastCleanAPI.Models;
using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.Reports
{
    public class ReportDTOFuncionario : ReportDTO
    {
        [Required]
        public int Reporter { get; set; }

        [Required]
        public int Reported { get; set; }
    }
}
