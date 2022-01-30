using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.Utilizador
{
    public class LatitudeLongitudeDTO
    {
        [Required]
        public string Latitude { get; set; }    

        [Required]
        public string Longitude { get; set; }
    }
}
