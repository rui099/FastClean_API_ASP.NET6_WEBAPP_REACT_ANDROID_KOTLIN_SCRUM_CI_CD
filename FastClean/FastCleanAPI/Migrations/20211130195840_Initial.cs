using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace FastCleanAPI.Migrations
{
    public partial class Initial : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Moradas",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Rua = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Numero = table.Column<int>(type: "int", nullable: false),
                    CodigoPostal = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Freguesia = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Concelho = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Distrito = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Moradas", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Utilizador",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Nome = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Password = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Cargo = table.Column<int>(type: "int", nullable: false),
                    email = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Contacto = table.Column<int>(type: "int", nullable: false),
                    MoradaId = table.Column<int>(type: "int", nullable: false),
                    CcFile = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Imagem = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Cadastro = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Discriminator = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    CartaDeConducao = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    HistoricoMedico = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    CvFile = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    Estado = table.Column<int>(type: "int", nullable: true),
                    Subscricao = table.Column<bool>(type: "bit", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Utilizador", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Utilizador_Moradas_MoradaId",
                        column: x => x.MoradaId,
                        principalTable: "Moradas",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                });

            migrationBuilder.CreateTable(
                name: "Chats",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    ClienteId = table.Column<int>(type: "int", nullable: false),
                    FuncionarioId = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Chats", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Chats_Utilizador_ClienteId",
                        column: x => x.ClienteId,
                        principalTable: "Utilizador",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                    table.ForeignKey(
                        name: "FK_Chats_Utilizador_FuncionarioId",
                        column: x => x.FuncionarioId,
                        principalTable: "Utilizador",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                });

            migrationBuilder.CreateTable(
                name: "Marcacoes",
                columns: table => new
                {
                    MarcacaoId = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    TipoImovel = table.Column<int>(type: "int", nullable: false),
                    TipoLimpeza = table.Column<int>(type: "int", nullable: false),
                    TipoAgendamento = table.Column<int>(type: "int", nullable: false),
                    NumQuartos = table.Column<int>(type: "int", nullable: false),
                    NumCasasDeBanho = table.Column<int>(type: "int", nullable: false),
                    Cozinha = table.Column<bool>(type: "bit", nullable: false),
                    Sala = table.Column<bool>(type: "bit", nullable: false),
                    Detalhes = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    DiaHora = table.Column<DateTime>(type: "datetime2", nullable: false),
                    ClienteId = table.Column<int>(type: "int", nullable: false),
                    FuncionarioId = table.Column<int>(type: "int", nullable: false),
                    MarcacaoAceitePeloFunc = table.Column<bool>(type: "bit", nullable: false),
                    MarcacaoAceitePeloCliente = table.Column<bool>(type: "bit", nullable: false),
                    Terminada = table.Column<bool>(type: "bit", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Marcacoes", x => x.MarcacaoId);
                    table.ForeignKey(
                        name: "FK_Marcacoes_Utilizador_ClienteId",
                        column: x => x.ClienteId,
                        principalTable: "Utilizador",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                    table.ForeignKey(
                        name: "FK_Marcacoes_Utilizador_FuncionarioId",
                        column: x => x.FuncionarioId,
                        principalTable: "Utilizador",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                });

            migrationBuilder.CreateTable(
                name: "ReportsClientes",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    ReporterId = table.Column<int>(type: "int", nullable: false),
                    ReportedId = table.Column<int>(type: "int", nullable: false),
                    Titulo = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Descricao = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Tipo = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ReportsClientes", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ReportsClientes_Utilizador_ReportedId",
                        column: x => x.ReportedId,
                        principalTable: "Utilizador",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                    table.ForeignKey(
                        name: "FK_ReportsClientes_Utilizador_ReporterId",
                        column: x => x.ReporterId,
                        principalTable: "Utilizador",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                });

            migrationBuilder.CreateTable(
                name: "ReportsFuncionario",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    ReporterId = table.Column<int>(type: "int", nullable: false),
                    ReportedId = table.Column<int>(type: "int", nullable: false),
                    Titulo = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Descricao = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Tipo = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ReportsFuncionario", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ReportsFuncionario_Utilizador_ReportedId",
                        column: x => x.ReportedId,
                        principalTable: "Utilizador",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                    table.ForeignKey(
                        name: "FK_ReportsFuncionario_Utilizador_ReporterId",
                        column: x => x.ReporterId,
                        principalTable: "Utilizador",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                });

            migrationBuilder.CreateTable(
                name: "ReportsFuncionarios",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    ReviewerId = table.Column<int>(type: "int", nullable: false),
                    ReviewedId = table.Column<int>(type: "int", nullable: false),
                    Comentario = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Cotacao = table.Column<int>(type: "int", nullable: false),
                    Data = table.Column<DateTime>(type: "datetime2", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ReportsFuncionarios", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ReportsFuncionarios_Utilizador_ReviewedId",
                        column: x => x.ReviewedId,
                        principalTable: "Utilizador",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                    table.ForeignKey(
                        name: "FK_ReportsFuncionarios_Utilizador_ReviewerId",
                        column: x => x.ReviewerId,
                        principalTable: "Utilizador",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                });

            migrationBuilder.CreateTable(
                name: "ReviewsClientes",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    ReviewerId = table.Column<int>(type: "int", nullable: false),
                    ReviewedId = table.Column<int>(type: "int", nullable: false),
                    Comentario = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Cotacao = table.Column<int>(type: "int", nullable: false),
                    Data = table.Column<DateTime>(type: "datetime2", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ReviewsClientes", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ReviewsClientes_Utilizador_ReviewedId",
                        column: x => x.ReviewedId,
                        principalTable: "Utilizador",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                    table.ForeignKey(
                        name: "FK_ReviewsClientes_Utilizador_ReviewerId",
                        column: x => x.ReviewerId,
                        principalTable: "Utilizador",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                });

            migrationBuilder.CreateTable(
                name: "Mensagens",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    SenderId = table.Column<int>(type: "int", nullable: false),
                    Text = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    ChatId = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Mensagens", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Mensagens_Chats_ChatId",
                        column: x => x.ChatId,
                        principalTable: "Chats",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                    table.ForeignKey(
                        name: "FK_Mensagens_Utilizador_SenderId",
                        column: x => x.SenderId,
                        principalTable: "Utilizador",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.NoAction);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Chats_ClienteId",
                table: "Chats",
                column: "ClienteId");

            migrationBuilder.CreateIndex(
                name: "IX_Chats_FuncionarioId",
                table: "Chats",
                column: "FuncionarioId");

            migrationBuilder.CreateIndex(
                name: "IX_Marcacoes_ClienteId",
                table: "Marcacoes",
                column: "ClienteId");

            migrationBuilder.CreateIndex(
                name: "IX_Marcacoes_FuncionarioId",
                table: "Marcacoes",
                column: "FuncionarioId");

            migrationBuilder.CreateIndex(
                name: "IX_Mensagens_ChatId",
                table: "Mensagens",
                column: "ChatId");

            migrationBuilder.CreateIndex(
                name: "IX_Mensagens_SenderId",
                table: "Mensagens",
                column: "SenderId");

            migrationBuilder.CreateIndex(
                name: "IX_ReportsClientes_ReportedId",
                table: "ReportsClientes",
                column: "ReportedId");

            migrationBuilder.CreateIndex(
                name: "IX_ReportsClientes_ReporterId",
                table: "ReportsClientes",
                column: "ReporterId");

            migrationBuilder.CreateIndex(
                name: "IX_ReportsFuncionario_ReportedId",
                table: "ReportsFuncionario",
                column: "ReportedId");

            migrationBuilder.CreateIndex(
                name: "IX_ReportsFuncionario_ReporterId",
                table: "ReportsFuncionario",
                column: "ReporterId");

            migrationBuilder.CreateIndex(
                name: "IX_ReportsFuncionarios_ReviewedId",
                table: "ReportsFuncionarios",
                column: "ReviewedId");

            migrationBuilder.CreateIndex(
                name: "IX_ReportsFuncionarios_ReviewerId",
                table: "ReportsFuncionarios",
                column: "ReviewerId");

            migrationBuilder.CreateIndex(
                name: "IX_ReviewsClientes_ReviewedId",
                table: "ReviewsClientes",
                column: "ReviewedId");

            migrationBuilder.CreateIndex(
                name: "IX_ReviewsClientes_ReviewerId",
                table: "ReviewsClientes",
                column: "ReviewerId");

            migrationBuilder.CreateIndex(
                name: "IX_Utilizador_MoradaId",
                table: "Utilizador",
                column: "MoradaId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Marcacoes");

            migrationBuilder.DropTable(
                name: "Mensagens");

            migrationBuilder.DropTable(
                name: "ReportsClientes");

            migrationBuilder.DropTable(
                name: "ReportsFuncionario");

            migrationBuilder.DropTable(
                name: "ReportsFuncionarios");

            migrationBuilder.DropTable(
                name: "ReviewsClientes");

            migrationBuilder.DropTable(
                name: "Chats");

            migrationBuilder.DropTable(
                name: "Utilizador");

            migrationBuilder.DropTable(
                name: "Moradas");
        }
    }
}
