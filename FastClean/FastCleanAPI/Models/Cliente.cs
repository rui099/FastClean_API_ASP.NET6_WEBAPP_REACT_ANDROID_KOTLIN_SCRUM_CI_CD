namespace FastCleanAPI.Models
{
    public class Cliente : Utilizador
    {
        public virtual List<ReportCliente>? ListaDeReports { get; set; }
        public virtual List<ReviewCliente>? ListaDeReviews { get; set; }
        

        public Cliente()
        {
            ListaDeReports = new List<ReportCliente>();
            ListaDeReviews = new List<ReviewCliente>(); 
        }
    }
}
