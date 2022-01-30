using FastCleanAPI.DTO_s.Utilizador;

namespace FastCleanAPI.DTO_s.Funcionario
{
    public class FuncionarioDetails : UtilizadorDetails
    {
        public double Preco { get; set; }

        public string TipoLimpeza { get; set; }

        public string EstadoFuncionario { get; set; }

        public double MediaReviews { get; set; }

        public string Longitude { get; set; }
        public string Latitude { get; set; }

        public string ValidadeSubscricao  { get; set; }
        public double distanciaAoCliente { get; set; }
    }
}
