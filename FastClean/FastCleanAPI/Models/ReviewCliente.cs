namespace FastCleanAPI.Models
{
    public class ReviewCliente : Review
    {
        public virtual Funcionario Reviewer { get; set; }
        public virtual Cliente Reviewed { get; set; }
    }
}
