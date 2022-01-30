using FastCleanAPI.Models;
using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.Utilizador
{
    public class MoradaDTO
    {
        public string Rua { get; set; }
        public int Numero { get; set; }
        public string CodigoPostal { get; set; }
        public string Freguesia { get; set; }
        public string Concelho { get; set; }
        public string Distrito { get; set; }

    }
}
