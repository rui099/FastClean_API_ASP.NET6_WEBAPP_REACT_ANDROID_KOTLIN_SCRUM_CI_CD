namespace FastCleanAPI.Models
{
    public class ReportFuncionario : Report
    {
        public virtual Cliente Reporter { get; set; }
        public virtual Funcionario Reported { get; set; }

    }
}
