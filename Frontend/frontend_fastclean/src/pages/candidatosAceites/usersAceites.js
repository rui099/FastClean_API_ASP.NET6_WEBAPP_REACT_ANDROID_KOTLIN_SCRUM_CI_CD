import React, {Component} from 'react';
import './usersAceites.css';
import Table from '@mui/material/Table';
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { maxWidth } from "@material-ui/system";
import {NavIcon } from './usersAceitesElements';
import axios from "axios";
import api from '../../services/api';
import { getCargo } from "../../services/auth";
import { FaSearch } from "react-icons/fa";


function refresh() {
    window.setTimeout(()=>{
        window.location.reload();
    }, 5);
  }

  class usersAceites extends Component {
    
    state = {
        aceites: [],
        pessoa: {},
        morada: {},
        found: false,
        email: ""
    };

    async componentDidMount() {
     
      if(getCargo() === "Administrador") {
      
        const response = await axios.get(
            "https://localhost:7067/api/Funcionarios/FuncionariosAceites"
        );
        
        this.setState({ aceites: response.data });
    
        }

    }

    
  filtrar = async e => {
    e.preventDefault();
    const {email} = this.state;
    if(email !== " "){
      const response2 = await api.get(
        "https://localhost:7067/api/Funcionarios/findFuncionarioByEmail/" + email
      );
      this.setState({pessoa: response2.data});
      this.setState({found: true});
        
    }
  }
  

    render(){

        const { aceites } = this.state;
        const { pessoa } = this.state;

      return (
    
         <div class="displayInfo" >
            <h1> Candidatos Aceites (Funcionários) </h1>
    
          <div class="filtroDiv3">
              <form style={{width: 750}} onSubmit={this.filtrar}>
                <label style={{fontSize: "medium"}} >Pesquise por Email: </label>
                <input class="emailInp3" type="email" name="email" placeholder= "abc@gmail.com" onChange={e => this.setState({ email: e.target.value })} />
                <button style={{width: 30, backgroundColor: "white", marginLeft:5}}> <FaSearch/> </button>
              </form>
          </div>
        
            <TableContainer component={Paper} style={{borderRadius: 50}}>
              <Table
                sx={{ minWidth: 650, maxWidth: 2000 }}
                size="small"
                aria-label="a dense table"
              >
    
                <TableHead> 
                  <TableRow>
                    <TableCell style={{textAlign:'center'}}> <h3> Imagem </h3></TableCell>
                    <TableCell style={{textAlign:'center'}}> <h3> Cargo </h3></TableCell>
                    <TableCell style={{textAlign:'center'}}> <h3> Nome </h3></TableCell>
                    <TableCell style={{textAlign:'center'}}> <h3> Idade </h3></TableCell>
                    <TableCell style={{textAlign:'center'}}> <h3> Freguesia </h3></TableCell>
                    <TableCell style={{textAlign:'center'}}> <h3> Distrito </h3></TableCell>
                  </TableRow>
                </TableHead>
    
                <TableBody>
                {(this.state.found===false) && aceites.map((aceite) => ( 
                    
                    <TableRow
                      key={aceites.nome}
                      sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                    >
                      <TableCell component="th" scope="row">
                       
                        <img 
                          src={`data:image/jpeg;base64,${aceite.imagem}`} 
                          width="120"
                          height="120"
                      	/>
    
                      </TableCell>
    
                      <TableCell style={{textAlign:'center', fontSize:18}}> {aceite.tipoUtilizador} </TableCell>
                      <TableCell style={{textAlign:'center', fontSize:18}}> {aceite.nome} </TableCell>
                      <TableCell style={{textAlign:'center', fontSize:18}}> {aceite.idade} anos </TableCell>
                      <TableCell style={{textAlign:'center', fontSize:18}}> {aceite.morada.freguesia} </TableCell>
                      <TableCell style={{textAlign:'center', fontSize:18}}> {aceite.morada.distrito} </TableCell>
      
                    </TableRow>
                  ))}
                   
                   {this.state.found===true && <TableRow
                      key={pessoa.nome}
                      sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                    >
                      <TableCell component="th" scope="row">
                      
                        <img 
                          src={`data:image/jpeg;base64,${pessoa.imagem}`} 
                          width="120"
                          height="120"
                        />

                      </TableCell>

                      <TableCell style={{textAlign:'center', fontSize:18}}> {pessoa.tipoUtilizador} </TableCell>
                      <TableCell style={{textAlign:'center', fontSize:18}}> {pessoa.nome} </TableCell>
                      <TableCell style={{textAlign:'center', fontSize:18}}> {pessoa.idade} anos </TableCell>
                      <TableCell style={{textAlign:'center', fontSize:18}}> {pessoa.morada.freguesia} </TableCell>
                      <TableCell style={{textAlign:'center', fontSize:18}}> {pessoa.morada.distrito} </TableCell>
                      
                    </TableRow> 
                  }    
                </TableBody>
              </Table>
            </TableContainer>
          </div>
        );
    }
}
    
export default usersAceites;