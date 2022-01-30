namespace FastCleanAPI.Models
{
    public class ReviewFuncionario : Review
    {
        public virtual Cliente Reviewer { get; set; }
        public virtual Funcionario Reviewed { get; set; }
    }
}
