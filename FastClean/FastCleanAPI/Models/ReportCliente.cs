namespace FastCleanAPI.Models
{
    public class ReportCliente : Report
    {
        public virtual Funcionario Reporter { get; set; }
        public virtual Cliente Reported { get; set; }

    }
}
