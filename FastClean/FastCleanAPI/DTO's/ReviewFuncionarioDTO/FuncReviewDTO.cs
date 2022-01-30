using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.ReviewFuncionarioDTO
{
    public class FuncReviewDTO
    {
        
        public int Id { get; set; }
        
        public string Comentario { get; set; }
        
        public double Cotacao { get; set; }
        public int Reviewer { get; set; }
        
        public int Reviewed { get; set; }
    }
}
