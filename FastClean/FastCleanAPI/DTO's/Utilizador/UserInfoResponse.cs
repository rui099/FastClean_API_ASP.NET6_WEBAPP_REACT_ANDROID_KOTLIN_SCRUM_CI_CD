using FastCleanAPI.Models;
using System.ComponentModel.DataAnnotations;

namespace FastCleanAPI.DTO_s.Utilizador
{
    public class UserInfoResponse
    {
        public int Id { get; set; }

        [Required]
        public string Nome { get; set; }

        [Required]
        public Morada Morada { get; set; }

        [Required]
        public string Email { get; set; }

        [Required]
        public string Cargo { get; set; }

        [Required]
        public string Token { get; set; }   
    }
}
