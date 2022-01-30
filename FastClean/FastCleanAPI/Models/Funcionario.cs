namespace FastCleanAPI.Models
{
    public class Funcionario : Utilizador
    {
        public double Preco { get; set; }
        public string CartaDeConducao { get; set; }
        public string HistoricoMedico { get; set; }

        public TipoLimpeza TipoLimpeza { get; set; }
        public string CvFile { get; set; }
        public EstadoFuncionario Estado { get; set; }
        public bool Subscricao { get; set; }

        public string? ValidadeSubscricao { get; set; }
        public virtual List<ReportFuncionario>? ListaDeReports {get;set;}
        public virtual List<ReviewFuncionario>? ListaDeReviews { get; set; }

        public Funcionario()
        {
            ListaDeReports = new List<ReportFuncionario>();
            ListaDeReviews = new List<ReviewFuncionario>();
        }
    }
}
