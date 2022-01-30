namespace FastCleanAPI.Models
{
    public class Report
    {
        
        public int Id { get; set; }
        public string Titulo { get; set; }
        public string Descricao { get; set; }
        public TipoReport Tipo { get; set; }
        public bool Arquivado { get; set; }
    }
}
