using FastCleanAPI.Models;
using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.Marcacao
{
    public class MarcacaoCasaApartamento
    {
        public TipoImovel TipoImovel { get; set; }
        public TipoLimpeza TipoLimpeza { get; set; }
        public TipoAgendamento TipoAgendamento  { get; set; }

        public int NumQuartos { get; set; }

        public string? Detalhes { get; set; }
       
        public string? DiaHora { get; set; }
        
        public int NumCasasDeBanho { get; set; }

        public bool Cozinha { get; set; }

        public bool Sala { get; set; }

        public int Cliente { get; set; }

        public int Funcionario { get; set; }

        public string MoradaMarcacao { get; set; }

        public string LatitudeMarcacao { get; set; }

        public string LongitudeMarcacao { get; set; }

        public int NumHorasPrevistas { get; set; }

     }
}
