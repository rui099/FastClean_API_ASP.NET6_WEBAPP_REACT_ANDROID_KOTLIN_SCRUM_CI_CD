namespace FastCleanAPI.DTO_s.ReviewFuncionarioDTO
{
    public class FuncionarioReviewDetails
    {
        public int Id { get; set; }

        public string Comentario { get; set; }

        public double Cotacao { get; set; }

        
        public string Data { get; set; }

        public int ReviewerID { get; set; }

        public string ReviewerName { get; set; }    

        public int ReviewedID { get; set; }

        public string ReviewedName { get; set; }
    }
}
