import React, { Component} from "react";
import './perfil.css';
import Table from '@mui/material/Table';
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import axios from "axios";
import {FaKey} from "react-icons/fa";
import {NavLink} from 'react-router-dom';
import {USER} from '../../services/auth';import { getUser} from "../../services/auth";
import Rating from '@mui/material/Rating';
import { Typography } from '@mui/material';


function createData(
    nome,
    avaliacao,
    review
  ) {
    return {nome, avaliacao, review };
  }

  function refresh() {
    window.setTimeout(()=>{
        window.location.reload();
    }, 5);
  }


class Perfil extends Component {
 

  state = {
    person: {},
    reviews: [],
    morada: {},
  };

  async componentDidMount() {
    const user = JSON.parse(getUser());
    const Id = user.id;

    if (user.cargo === "Cliente") {
      const response = await axios.get(
        "https://localhost:7067/api/DetailsClientes/" + Id
      );
      this.setState({ person: response.data });
      this.setState({ morada: response.data.morada });
    }else{
      const response = await axios.get(
        "https://localhost:7067/api/DetailsFuncionarios/" + Id
      );
      this.setState({ person: response.data });
      this.setState({ morada: response.data.morada });
    }

    if (user.cargo === "Cliente") {
      const response2 = await axios.get(
        "https://localhost:7067/api/Clientes/reviewsCliente/" + Id
      );
      this.setState({ reviews: response2.data });
      
    }else{
      const response2 = await axios.get(
        "https://localhost:7067/api/Funcionarios/reviewsFuncionario/" + Id
      );
      this.setState({ reviews: response2.data });
      
    }
  }

  render() {
    const { person } = this.state;
    const { morada } = this.state;
    const { reviews } = this.state;

    console.log(morada);
    return (
      <div class="displayInfo">
        <br/>
        <div class="perfilInfo">
        <NavLink style={{marginLeft: 40,width: 80, color: "black" ,textDecoration: "none",  borderRadius: 100, backgroundColor: "lightblue", paddingLeft: 10, paddingRight: 10, paddingTop: 10 ,paddingBottom:10}} to= "/editPass"> <FaKey/> Editar Password</NavLink>
       {person.tipoUtilizador==="Cliente" && <NavLink style={{marginLeft: 20,width: 80, color: "black" ,textDecoration: "none",  borderRadius: 100, backgroundColor: "lightblue", paddingLeft: 10, paddingRight: 10, paddingTop: 10 ,paddingBottom:10}} to= {"/changePersonalDataCli/" +  person.id + "/" + person.email} onClick={refresh} > <FaKey/> Editar Dados Pessoais</NavLink>}
       {person.tipoUtilizador==="Funcionario" && <NavLink style={{marginLeft: 20,width: 80, color: "black" ,textDecoration: "none",  borderRadius: 100, backgroundColor: "lightblue", paddingLeft: 10, paddingRight: 10, paddingTop: 10 ,paddingBottom:10}} to= {"/changePersonalDataFunc/" +  person.id + "/" + person.email} onClick={refresh} > <FaKey/> Editar Dados Pessoais</NavLink>}<br/><br/>
          <div class="div2">
            <img
              src={`data:image/jpeg;base64,${person.imagem}`} 
              width="150"
              height="150"
            />
          </div>
          <div class="descricao">
            <h1>
              {person.nome}, {person.idade} anos {" "}
            </h1>
           <h3> {morada.rua} nº {morada.numero} - {morada.codigoPostal}<br/> </h3>
           <h3> {morada.freguesia}, {morada.concelho}, {morada.distrito}<br/> </h3>
           <h3>  {person.contacto} | {person.email}</h3>
         { person.tipoUtilizador==="Funcionario" && <h4> Preço p/Serviço: {person.preco} € </h4> }
          </div>
        
        <hr />
        <div class="div1">
          <div class="div2">
            <h2>Reviews</h2>
            <TableContainer component={Paper} border="1px">
              <Table
                sx={{ minWidth: 800, maxWidth: 1000 }}
                size="small"
                aria-label="a dense table"
              >
                <TableBody >
                  {reviews.map((review) => (
                    <TableRow
                      key={review.reviewedName}
                      sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                    >
                      <TableCell component="th" scope="row">
                        <h3>
                        <span class="tutorial">Reviewer: </span>{review.reviewerName} 
                        </h3>{" "}
                        <Typography component="legend"><span class="tutorial">Avaliação:</span></Typography>
                        <Rating name="read-only" value={review.cotacao} readOnly />
                        <h3>
                          <span class="tutorial">Comentário:</span> {review.comentario}
                        </h3>{" "}
                        <hr />
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </div>
        </div>
        </div>
      </div>
    );
  }
};

export default Perfil;