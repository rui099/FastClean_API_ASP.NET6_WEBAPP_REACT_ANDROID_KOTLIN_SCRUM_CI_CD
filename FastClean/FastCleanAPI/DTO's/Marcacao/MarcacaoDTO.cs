using FastCleanAPI.Models;
using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.Marcacao

{
    public class MarcacaoDTO
    {
        
        public int MarcacaoId { get; set; }
        public string TipoImovel { get; set; }
        public string TipoLimpeza { get; set; }
        public string TipoAgendamento { get; set; }
        public int NumQuartos { get; set; }
        public int NumCasasDeBanho { get; set; }
        public string Cozinha { get; set; }
        public string Sala { get; set; }
        public string? Detalhes { get; set; }
        public string EstadoFuncionario { get; set; }
        public string DiaHora { get; set; }
        public string HoraInicio { get; set; } 
        public string HoraFinal { get; set; }       
        public string Cliente { get; set; }
        public int ClienteID { get; set; }
        public string Funcionario { get; set; }
        public int FuncionarioID { get; set; }
        public string MarcacaoAceitePeloFunc { get; set; }
        public string MarcacaoAceitePeloCliente { get; set; }
        public string Terminada { get; set; }
        public int NumHorasPrevistas { get; set; }
        public string MoradaMarcacao { get; set; }
        public string LatitudeMarcacao { get; set; }
        public string LongitudeMarcacao {get; set; }
        public string Duracao { get; set; }
        public bool ReviewCliente { get; set; }
        public bool ReviewFuncionario { get; set; }
        public double Total { get; set; }

    }

    


}
