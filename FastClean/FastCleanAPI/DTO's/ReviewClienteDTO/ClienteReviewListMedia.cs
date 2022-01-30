using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.ReviewClienteDTO
{
    public class ClienteReviewListMedia
    {
        [Required]
        public double Media { get; set; }
        [Required]
        public List<ReviewClienteDetails> ListaReviews { get; set; }

        public ClienteReviewListMedia()
        {
            ListaReviews = new List<ReviewClienteDetails>();
        }
    }

    
}
