using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using FastCleanAPI.Models;
using FastCleanAPI.DTO_s.Cliente;
using Microsoft.AspNetCore.Authorization;
using FastCleanAPI.DTO_s.ReviewClienteDTO;
using FastCleanAPI.DTO_s.Utilizador;

namespace FastCleanAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ClientesController : ControllerBase
    {
        private readonly FastCleanContext _context;
        private readonly IWebHostEnvironment _hostingEnv;

        /// <summary>
        /// Metodo Construtor
        /// </summary>
        /// <param name="context"></param>
        /// <param name="hostingEnv"></param>
        public ClientesController(FastCleanContext context, IWebHostEnvironment hostingEnv)
        {
            _context = context;
            _hostingEnv = hostingEnv;
        }

        
        /// <summary>
        /// Este metodo vai permitir criar um Cliente
        /// </summary>
        /// <param name="clienteDTO"></param>
        /// <returns>Ok caso o cliente seja criado </returns>
        // POST: api/Clientes
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        [AllowAnonymous]
        public async Task<ActionResult> PostCliente([FromForm] ClienteRegister clienteDTO)
        {
            string image = uploadImage(clienteDTO);
            string cadastro = uploadCadastro(clienteDTO);
            string cFile = uploadCcFile(clienteDTO);

            if (!image.EndsWith(".png") && !image.EndsWith(".jpg") && !image.EndsWith(".PNG"))
            {
                return BadRequest("Só são aceiteis imagens no formato PNG ou JPG");
            }

            if(!cadastro.EndsWith(".pdf"))
            {
                return BadRequest("O ficheiro do cadastro tem de ser em pdf!!!");
            }

            if (!cFile.EndsWith(".pdf"))
            {
                return BadRequest("O ficheiro do Cartão de Cidadão tem de ser enviado em pdf");
            }

            string password = BCrypt.Net.BCrypt.HashPassword(clienteDTO.Password,12);


            var today = DateTime.Today;

            int idade = today.Year - clienteDTO.Idade.Year;

            List<Utilizador> lista = await _context.Utilizador.ToListAsync();

            foreach(var c in lista)
            {
                if(c.Email == clienteDTO.Email)
                {
                    return BadRequest("Já existe um email repetido."); 
                }
            }

            if (clienteDTO != null)
            {
                Cliente cliente = new Cliente
                {
                    Nome = clienteDTO.Nome,
                    Password = password,
                    Email = clienteDTO.Email,
                    Idade = idade,
                    Cargo = Cargos.Cliente,
                    Morada =clienteDTO.Morada,
                    Contacto = clienteDTO.Contacto,
                    CcFile = cFile,
                    Imagem = image,
                    Cadastro = cadastro,
                    Banido = false,
                    Aceite = false
                };

                _context.Add(cliente);
                await _context.SaveChangesAsync();
                return Ok(cliente);
            }
            
            return BadRequest();
        }

        /// <summary>
        /// Metodo que permite alterar a Imagem do Cliente
        /// </summary>
        /// <param name="id">Id Do cliente</param>
        /// <param name="clienteUpdate">Informação que vai ser alterada do Cliente</param>
        /// <param name="email">Email do Cliente que queremos mudar</param>
        /// <returns>Retorna o Cliente com a imagem alterada alterado</returns>
        // PUT: api/Clientes/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("AtualizarClienteImagem/{id}/{email}")]
        //[Authorize("Cliente")]
        public async Task<ActionResult> PutImagemCliente([FromForm] ImageDTO clienteUpdate, int id, string email)
        {
            Cliente cliente = await _context.Clientes.FindAsync(id);
            string imagem = null;

            if (clienteUpdate.Imagem.FileName.EndsWith(".jpg") || clienteUpdate.Imagem.FileName.EndsWith(".png") || clienteUpdate.Imagem.FileName.EndsWith(".PNG"))
            {
                imagem = updateUploadFile(clienteUpdate, email);
                string file = Path.Combine(_hostingEnv.WebRootPath, "Images/", email, cliente.Imagem);
                System.IO.File.Delete(file);
            }
            else
            {
                return BadRequest("Tem de ser um ficheiro no formato JPG e PNG.");
            }
            
            if (cliente == null)
            {
                return NotFound("Este Cliente não existe na Base de Dados");
            }


            if (cliente.Email != email)
            {
                return NotFound("Este email não corresponde ao email do Utilizador!!!");
            }

            cliente.Imagem = imagem;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!ClienteExists(id))
            {
                return NotFound();
            }

            return Ok(cliente);
        }

        /// <summary>
        /// Metodo que permite alterar o Cadastro do Cliente
        /// </summary>
        /// <param name="id">Id Do cliente</param>
        /// <param name="clienteUpdate">Informação que vai ser alterada do Cliente</param>
        /// <param name="email">Email do Cliente que queremos mudar</param>
        /// <returns>Retorna o Cliente com a imagem alterada alterado</returns>
        // PUT: api/Clientes/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("AtualizarClienteCadastro/{id}/{email}")]
        //[Authorize("Cliente")]
        public async Task<ActionResult> PutCadastroCliente([FromForm] ImageDTO clienteUpdate, int id, string email)
        {
            Cliente cliente = await _context.Clientes.FindAsync(id);
            string imagem = null;

            if (clienteUpdate.Imagem.FileName.EndsWith(".pdf"))
            {
                imagem = updateUploadFile(clienteUpdate, email);
                string file = Path.Combine(_hostingEnv.WebRootPath, "Images/", email, cliente.Cadastro);
                System.IO.File.Delete(file);
            }
            else
            {
                return BadRequest("Tem de ser um ficheiro no formato PDF.");
            }

           
            if (cliente == null)
            {
                return NotFound("Este Cliente não existe na Base de Dados");
            }


            if (cliente.Email != email)
            {
                return NotFound("Este email não corresponde ao email do Utilizador!!!");
            }

            

            cliente.Cadastro = imagem;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!ClienteExists(id))
            {
                return NotFound();
            }

            return Ok(cliente);
        }


        /// <summary>
        /// Metodo que permite alterar o CcFile do Cliente
        /// </summary>
        /// <param name="id">Id Do cliente</param>
        /// <param name="clienteUpdate">Informação que vai ser alterada do Cliente</param>
        /// <param name="email">Email do Cliente que queremos mudar</param>
        /// <returns>Retorna o Cliente com a CcFile alterada alterado</returns>
        // PUT: api/Clientes/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("AtualizarClienteCcfile/{id}/{email}")]
        //[Authorize("Cliente")]
        public async Task<ActionResult> PutCcfileCliente([FromForm] ImageDTO clienteUpdate, int id, string email)
        {
            Cliente cliente = await _context.Clientes.FindAsync(id);
            string imagem = null;

            if (clienteUpdate.Imagem.FileName.EndsWith(".pdf"))
            {
                imagem = updateUploadFile(clienteUpdate, email);
                string file = Path.Combine(_hostingEnv.WebRootPath, "Images/", email, cliente.CcFile);
                System.IO.File.Delete(file);
            }
            else
            {
                return BadRequest("Tem de ser um ficheiro no formato PDF.");
            }

            if (cliente == null)
            {
                return NotFound("Este Cliente não existe na Base de Dados.");
            }


            if (cliente.Email != email)
            {
                return NotFound("Este email não corresponde ao email do Utilizador!!!");
            }

    
            cliente.CcFile = imagem;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException) when (!ClienteExists(id))
            {
                return NotFound();
            }

            return Ok(cliente);
        }

        

        /// <summary>
        /// Este metodo vai fazer upload de imagem
        /// </summary>
        /// <param name="model"></param>
        /// <returns>O Path de esta a imagem</returns>
        private string uploadImage(ClienteRegister model)
        {
            string uniqueFileName = null;

            if (model.Imagem != null)
            {
                string uploadsFolder = Path.Combine(_hostingEnv.WebRootPath, "Images/", model.Email);

                System.IO.Directory.CreateDirectory(uploadsFolder);

                uniqueFileName = Guid.NewGuid().ToString() + "_" + model.Imagem.FileName;

                string filePath = Path.Combine(uploadsFolder, uniqueFileName);

                using (var fileStream = new FileStream(filePath, FileMode.Create))
                {
                    model.Imagem.CopyTo(fileStream);
                }
            }
            return uniqueFileName;
        }

        /// <summary>
        /// Metodo para atualizar Imagem do Cliente
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        private string updateUploadFile(ImageDTO model, string email)
        {
            string uniqueFileName = null;

            if (model.Imagem != null)
            {
                string uploadsFolder = Path.Combine(_hostingEnv.WebRootPath, "Images/", email);

                bool exists = System.IO.Directory.Exists(uploadsFolder);

                if (exists)
                {
                    uniqueFileName = Guid.NewGuid().ToString() + "_" + model.Imagem.FileName;

                    string filePath = Path.Combine(uploadsFolder, uniqueFileName);

                    using (var fileStream = new FileStream(filePath, FileMode.Create))
                    {
                        model.Imagem.CopyTo(fileStream);
                    }
                }
            }

            return uniqueFileName;
        }
        /// <summary>
        /// Método para adicionar ficheiro do Cadastro do Cliente
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        private string uploadCadastro(ClienteRegister model)
        {
            string uniqueFileName = null;

            if (model.Cadastro != null)
            {
                string uploadsFolder = Path.Combine(_hostingEnv.WebRootPath, "Images/", model.Email);

                System.IO.Directory.CreateDirectory(uploadsFolder);

                uniqueFileName = Guid.NewGuid().ToString() + "_" + model.Cadastro.FileName;

                string filePath = Path.Combine(uploadsFolder, uniqueFileName);

                using (var fileStream = new FileStream(filePath, FileMode.Create))
                {
                    model.Cadastro.CopyTo(fileStream);
                }
            }

            return uniqueFileName;
        }

      

        /// <summary>
        /// Metodo para fazer upload do CcFile para o Cliente
        /// </summary>
        /// <param name="model"></param>
        /// <returns></returns>
        private string uploadCcFile(ClienteRegister model)
        {
            string uniqueFileName = null;

            if (model.Ccfile != null)
            {
                string uploadsFolder = Path.Combine(_hostingEnv.WebRootPath, "Images/", model.Email);

                System.IO.Directory.CreateDirectory(uploadsFolder);

                uniqueFileName = Guid.NewGuid().ToString() + "_" + model.Ccfile.FileName;

                string filePath = Path.Combine(uploadsFolder, uniqueFileName);

                using (var fileStream = new FileStream(filePath, FileMode.Create))
                {
                    model.Ccfile.CopyTo(fileStream);
                }
            }
            return uniqueFileName;
        }

        

        /// <summary>
        /// Este método vai retornar um Cliente especifico
        /// </summary>
        /// <param name="id">Id do Cliente que desejamos obter</param>
        /// <returns>NotFound caso o Cliente não exista</returns>
        /// <returns>Ok caso o Cliente exista</returns>
        // GET: api/Clientes/5
        [HttpGet("{id}")]
        //[Authorize(Roles ="Cliente, Funcionario")]
        public async Task<ActionResult<ClienteDetails>> GetCliente(int id)
        {
            Cliente cliente = await _context.Clientes.FindAsync(id);

            if (cliente == null)
            {
                return NotFound("O Cliente não existe na Base de Dados");
            }

            var imagem = imageToByteArray(cliente.Imagem, cliente.Email);
            
            ClienteDetails clienteDetails = new ClienteDetails
            {
                Nome = cliente.Nome,
                Idade = cliente.Idade,
                Id = cliente.Id,
                Contacto = cliente.Contacto,
                Morada = cliente.Morada,
                Email = cliente.Email,
                CcFile = cliente.CcFile,
                Cadastro = cliente.Cadastro,
                Imagem = imagem,
                TipoUtilizador = cliente.Cargo.ToString()
            };

            return Ok(clienteDetails);
        }

        /// <summary>
        /// Este método serve para converter uma imagem.
        /// </summary>
        /// <param name="imageName">imageName nome da imagem</param>
        /// <returns></returns>
        private byte[] imageToByteArray(string imageName, string userEmail)
        {
            var path = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot/Images", userEmail, imageName);
            Byte[] stream = System.IO.File.ReadAllBytes(path);
            return stream;
        }


        /// <summary>
        /// Metodo para mostrar as reviews de um Cliente
        /// </summary>
        /// <param name="id">Id do cliente que queremos obter a listagem</param>
        /// <returns></returns>
        // GET: api/Clientes/5
        [HttpGet("reviewsCliente/{id}")]
        //[Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<IEnumerable<ReviewClienteDetails>>> getReviewsCliente(int id)
        {
            var cliente = await _context.Clientes.FindAsync(id);

            if (cliente == null)
            {
                return NotFound("Este Cliente não existe!!");
            }


            List<ReviewClienteDetails> listaReviews = new List<ReviewClienteDetails>();

            foreach(var review in cliente.ListaDeReviews)
            {
                listaReviews.Add(new ReviewClienteDetails {
                    Id = review.Id,
                    Comentario = review.Comentario,
                    Cotacao = review.Cotacao,
                    ReviewedID = review.Reviewed.Id,
                    ReviewedName = review.Reviewed.Nome,
                    ReviewerID = review.Reviewer.Id,
                    ReviewerName = review.Reviewer.Nome,
                    Data = review.Data
                });
            }

            return listaReviews;
        }

        /// <summary>
        /// Meotodo para obter media das reviews do Cliente
        /// </summary>
        /// <param name="id">Id de Cliente que queremos obter a media</param>
        /// <returns></returns>
        [HttpGet("mediaDeReviewsCliente/{id}")]
        ///[Authorize(Roles = "Cliente, Funcionario")]
        public async Task<ActionResult<double>> getMediaReviews(int id)
        {
            var cliente = await _context.Clientes.FindAsync(id);

            int count = 0;
            double media = 0;

            foreach (var r in cliente.ListaDeReviews)
            {
                media = media + r.Cotacao;
                count++;
            }

            double temp = media / count;
            return temp;
        }

        /// <summary>
        /// Este metodo vai retornar todos Clientes não aceites pelo o administrador
        /// </summary>
        /// <returns>A lista de Utilizadores não aceites pelo administrador</returns>
        [HttpGet("ClienteAceites")]
        public async Task<List<ClienteDetails>> ListaDeFuncionariosAceites()
        {
            var listaDeUtilizadores = await _context.Clientes.ToListAsync();

            var listaDeUtilizadoresAceites = new List<ClienteDetails>();


            foreach (var c in listaDeUtilizadores)
            {
                if (c.Aceite == true)
                {
                    var image = imageToByteArray(c.Imagem, c.Email);

                    ClienteDetails utilizador = new ClienteDetails
                    {
                        Id = c.Id,
                        Nome = c.Nome,
                        Imagem = image,
                        Morada = c.Morada,
                        Cadastro = c.Cadastro,
                        CcFile = c.CcFile,
                        Email = c.Email,
                        Idade = c.Idade,
                        TipoUtilizador = c.Cargo.ToString()
                    };
                    listaDeUtilizadoresAceites.Add(utilizador);
                }
            }

            return listaDeUtilizadoresAceites;
        }


        

        /// <summary>
        /// Este metodo vai retornar todos Clientes não aceites pelo o administrador
        /// </summary>
        /// <returns>A lista de Utilizadores não aceites pelo administrador</returns>
        [HttpGet("ClienteNaoAceites")]
        public async Task<List<ClienteDetails>> ListaDeClientesNaoAceites()
        {
            var listaDeUtilizadores = await _context.Clientes.ToListAsync();

            var listaDeUtilizadoresAceites = new List<ClienteDetails>();


            foreach (var c in listaDeUtilizadores)
            {
                if (c.Aceite == false)
                {
                    var image = imageToByteArray(c.Imagem, c.Email);

                    ClienteDetails utilizador = new ClienteDetails
                    {
                        Id = c.Id,
                        Nome = c.Nome,
                        Imagem = image,
                        Morada = c.Morada,
                        Cadastro = c.Cadastro,
                        CcFile = c.CcFile,
                        Email = c.Email,
                        Idade = c.Idade,
                        TipoUtilizador = c.Cargo.ToString()
                    };
                    listaDeUtilizadoresAceites.Add(utilizador);
                }
            }

            return listaDeUtilizadoresAceites;
        }

       

       

        /// <summary>
        /// Este método vai retornar um Cliente especifico
        /// </summary>
        /// <param name="id">Id do Cliente que desejamos obter</param>
        /// <returns>NotFound caso o Cliente não exista</returns>
        /// <returns>Ok caso o Cliente exista</returns>
        // GET: api/Clientes/5
        [HttpGet("findClienteByEmail/{email}")]
        //[Authorize(Roles ="Cliente, Funcionario")]
        public async Task<ActionResult<ClienteDetails>> GetClienteByEmail(string email)
        {
            var clienteLista = await _context.Clientes.ToListAsync();

            Cliente cliente = new Cliente();

            foreach(var clienteTemp in clienteLista)
            {
                if(clienteTemp.Email == email)
                {
                    cliente = clienteTemp;
                }
            }

            var imagem = imageToByteArray(cliente.Imagem, cliente.Email);

            ClienteDetails clienteDetails = new ClienteDetails
            {
                Nome = cliente.Nome,
                Idade = cliente.Idade,
                Id = cliente.Id,
                Contacto = cliente.Contacto,
                Morada = cliente.Morada,
                Email = cliente.Email,
                CcFile = cliente.CcFile,
                Cadastro = cliente.Cadastro,
                Imagem = imagem,
                TipoUtilizador = cliente.Cargo.ToString()
            };

            return clienteDetails;
        }
        /// <summary>
        /// Metodo que retorna um Boolean quase exista o Cliente ou não
        /// </summary>
        /// <param name="id"></param>
        /// <returns>True se existir o Cliente, false se não</returns>
        private bool ClienteExists(int id)
        {
            return _context.Clientes.Any(e => e.Id == id);
        }

    }
}
