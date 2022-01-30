namespace FastCleanAPI.Models
{
    public abstract class Review
    {
        public  int Id { get; set; }
        public string? Comentario { get; set; }
        public double Cotacao { get; set; }
        public string? Data { get; set; }
    }
}
