namespace FastCleanAPI.Models
{
    public class Morada
    {
        public int Id { get; set; }
        public string Rua{ get; set; }
        public int Numero{ get; set; }
        public string CodigoPostal { get; set; }
        public string Freguesia { get; set; }
        public string Concelho { get; set; }
        public string Distrito { get; set; }
    }
}
