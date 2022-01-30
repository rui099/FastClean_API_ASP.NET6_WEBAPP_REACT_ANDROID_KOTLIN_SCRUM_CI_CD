using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace FastCleanAPI.Migrations
{
    public partial class MoradaMarcacao : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "LatitudeMarcacao",
                table: "Marcacoes",
                type: "nvarchar(max)",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "LongitudeMarcacao",
                table: "Marcacoes",
                type: "nvarchar(max)",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "MoradaMarcacao",
                table: "Marcacoes",
                type: "nvarchar(max)",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "LatitudeMarcacao",
                table: "Marcacoes");

            migrationBuilder.DropColumn(
                name: "LongitudeMarcacao",
                table: "Marcacoes");

            migrationBuilder.DropColumn(
                name: "MoradaMarcacao",
                table: "Marcacoes");
        }
    }
}
