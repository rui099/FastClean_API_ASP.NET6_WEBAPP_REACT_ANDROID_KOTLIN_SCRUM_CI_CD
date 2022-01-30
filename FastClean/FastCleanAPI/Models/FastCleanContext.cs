using Microsoft.EntityFrameworkCore;
using System.Diagnostics.CodeAnalysis;
using FastCleanAPI.Models;

namespace FastCleanAPI.Models
{
    public class FastCleanContext : DbContext
    {
        public FastCleanContext(DbContextOptions<FastCleanContext> options) : base(options)
        {

        }

        
        public DbSet<Chat> Chats { get; set; } = null;

        public DbSet<Cliente> Clientes { get; set; } = null;

        public DbSet<Funcionario> Funcionarios { get; set; } = null;

        public DbSet<Marcacao> Marcacoes { get; set; } = null;

        public DbSet<Mensagem> Mensagens { get; set; } = null;

        public DbSet<Morada> Moradas { get; set; } = null;

        public DbSet<Utilizador> Utilizador { get; set;} = null;

        public DbSet<ReportFuncionario> ReportsFuncionario { get; set; } = null;

        public DbSet<ReportCliente> ReportsClientes { get; set; } = null;

        public DbSet<ReviewCliente> ReviewsClientes { get; set; } = null;

        public DbSet<ReviewFuncionario> ReviewsFuncionarios { get; set; } = null;

       
    }
}
