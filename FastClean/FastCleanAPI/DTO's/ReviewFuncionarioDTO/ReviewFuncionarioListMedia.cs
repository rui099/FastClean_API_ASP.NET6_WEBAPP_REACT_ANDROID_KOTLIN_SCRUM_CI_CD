using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.ReviewFuncionarioDTO
{
    public class ReviewFuncionarioListMedia
    {
        [Required]
        public double Media { get; set; }
        [Required]
        public List<FuncionarioReviewDetails> ListaReviews { get; set; }

        public ReviewFuncionarioListMedia()
        {
            ListaReviews = new List<FuncionarioReviewDetails>();
        }
    }
}
