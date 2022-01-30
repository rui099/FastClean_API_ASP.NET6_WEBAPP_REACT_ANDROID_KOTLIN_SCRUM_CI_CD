using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.Models
{
    public class Marcacao
    {
        public int MarcacaoId { get; set; }
        public TipoImovel TipoImovel { get; set; }
        public TipoLimpeza TipoLimpeza { get; set; }
        public TipoAgendamento TipoAgendamento { get; set; }
        public int NumQuartos { get; set; }
        public int NumCasasDeBanho { get; set; }
        public bool Cozinha { get; set; }
        public bool Sala { get; set; }
        public string? HoraInicial { get; set; }
        public string? HoraFinal { get; set; }
        public string? Detalhes { get; set; }
        public string? DiaHora { get; set; }
        public virtual Cliente? Cliente { get; set; }
        public virtual Funcionario? Funcionario { get; set; }
        public bool MarcacaoAceitePeloFunc { get; set; }
        public bool MarcacaoAceitePeloCliente { get; set; }
        public bool Terminada { get; set; }
        public string? MoradaMarcacao { get; set; }
        public string? LatitudeMarcacao { get; set; }
        public string? LongitudeMarcacao { get; set; }
        public string? DuracaoTotal { get; set; }
        public int NumHorasPrevistas { get; set; }
        public double Total { get; set; }
        public bool CliReview{ get; set; }
        public bool FuncReview { get; set; }

    }
}
