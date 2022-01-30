using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace FastCleanAPI.Migrations
{
    public partial class ReportsAquirvo : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "Arquivado",
                table: "ReportsFuncionario",
                type: "bit",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "Arquivado",
                table: "ReportsClientes",
                type: "bit",
                nullable: false,
                defaultValue: false);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Arquivado",
                table: "ReportsFuncionario");

            migrationBuilder.DropColumn(
                name: "Arquivado",
                table: "ReportsClientes");
        }
    }
}
