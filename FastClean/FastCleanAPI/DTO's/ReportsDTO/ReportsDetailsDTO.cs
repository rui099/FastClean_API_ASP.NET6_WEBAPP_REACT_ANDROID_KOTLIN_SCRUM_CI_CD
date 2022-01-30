using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.ReportsDTO
{
    public class ReportsDetailsDTO
    {
        [Required]
        public string Titulo;

        [Required]
        public string Descricao;

        [Required]
        public string Tipo;

        [Required]
        public string Cliente;

        [Required]
        public string Funcionario;

        public string Arquivado;


    }
}
