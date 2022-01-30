using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.ReviewClienteDTO
{
    public class ClienteReviewDTO
    {
        
        public int Id { get; set; }
        [Required]
        public string Comentario { get; set; }
        [Required]
        public double Cotacao { get; set; }

        [Required]
        public int Reviewer { get; set; }
        [Required]
        public int Reviewed { get; set; }
    }
}
