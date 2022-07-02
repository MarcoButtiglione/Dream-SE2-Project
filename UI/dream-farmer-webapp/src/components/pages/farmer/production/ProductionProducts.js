import {
    Container,
    Table,
    Select,
    Button,
    Icon,
    Modal,
    ModalHeader,
    ModalBody,
    ModalFooter,
    Input, TextArea, notify
} from "design-react-kit";
import React from "react";
import {Fab, IconButton} from "@mui/material";
import ApiClient from "../../../../commons/apiClient";





export class ProductionProducts extends React.Component{
    constructor(props) {
        super(props);
        this.state=this.getDefaultState()
        this.client = new ApiClient()
    }
    getDefaultState = () => {
        return {
            selectedOption:null,
            products:null,
            productsForSelect:{
                value:"",
                label:""
            },
            productions:null,

            openModalNewProduct:false,
            nameNewProduct:"",
            typeNewProduct:"",
            specificsNewProduct:"",

            openModalNewProduction:false,
            amountNewProduction:"",
            noteNewProduction:"",

            openModalDeleteProduct:false,

            openModalEditProduct:false,
            nameEditProduct:"",
            typeEditProduct:"",
            specificsEditProduct:"",

            openModalEditProduction:false,
            amountEditProduction:"",
            noteEditProduction:"",

            openModalDeleteProduction:false,
            idDeleteProduction:""
        }
    }
    componentDidMount() {
        this.getProduct()
    }


    getProduct = () => {
        this.client.GetProductsByFarm()
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        products: response.data.results[0],
                    }, function() {
                        const productsForSelect=this.state.products.map((product)=>{
                            return { value:product.productId,label:product.product}})
                        this.setState({
                            productsForSelect:productsForSelect
                        })
                    })

                }else if(response.data.code==404){
                    this.setState({
                        products: null,
                    })
                }else if(response.data.code==400){
                    this.setState({
                        products: null,
                    })
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }

            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
        ;
    };
    getProductionsByProduct = () => {
        this.client.GetProductionsByFarmAndProduct(this.state.selectedOption.value)
            .then((response) => {
                if(response.data.code==200){
                    this.setState({
                        productions: response.data.results[0],
                    })

                }else if(response.data.code==404){
                    this.setState({
                        productions: null,
                    })
                }else if(response.data.code==400){
                    this.setState({
                        productions: null,
                    })
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }

            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
        ;
    };
    postProduct=()=>{
        const product ={
            name:this.state.nameNewProduct,
            type:this.state.typeNewProduct,
            specifics:this.state.specificsNewProduct,
        }
        this.client.PostProduct(product)
            .then((response) => {
                if(response.data.code==200){
                    this.getProduct()
                    notify(
                        'Success',
                        'The Product was successfully created',
                        {state:"success"}
                    )
                }else if(response.data.code==404){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }
                this.handleToggleModalNewProduct()


            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
        ;

    }
    putProduct=()=>{
        const product ={
            name:this.state.nameEditProduct,
            type:this.state.typeEditProduct,
            specifics:this.state.specificsEditProduct,
        }
        this.client.PutProduct(product,this.state.selectedOption.value)
            .then((response) => {
                if(response.data.code==200){
                    this.setState({selectedOption:null})
                    this.getProduct()
                    notify(
                        'Success',
                        'The Product was successfully edited',
                        {state:"success"}
                    )
                }else if(response.data.code==404){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }
                this.handleToggleModalEditProduct()


            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
        ;

    }
    postProduction=()=>{
        const production ={
            amount:this.state.amountNewProduction,
            note:this.state.noteNewProduction,
        }
        this.client.PostProduction(production,this.state.selectedOption.value)
            .then((response) => {
                if(response.data.code==200){
                    this.getProductionsByProduct()
                    notify(
                        'Success',
                        'The Production was successfully created',
                        {state:"success"}
                    )
                }else if(response.data.code==404){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }
                this.handleToggleModalNewProduction()

            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
        ;

    }
    putProduction=()=>{
        const production ={
            amount:this.state.amountEditProduction,
            note:this.state.noteEditProduction,
        }
        this.client.PutProduction(production,this.state.selectedOption.value,this.state.idEditProduction)
            .then((response) => {
                if(response.data.code==200){
                    this.getProductionsByProduct()
                    notify(
                        'Success',
                        'The Production was successfully edited',
                        {state:"success"}
                    )
                }else if(response.data.code==404){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }
                this.handleToggleModalEditProduction()
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
        ;

    }
    deleteProduct=()=>{
        this.client.DeleteProduct(this.state.selectedOption.value)
            .then((response) => {
                if(response.data.code==200){
                    this.setState({productsForSelect:null},()=>{
                            this.getProduct()
                        })

                    notify(
                        'Success',
                        'The Product was successfully deleted',
                        {state:"success"}
                    )

                }else if(response.data.code==404){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }
                this.setState({openModalDeleteProduct:false,selectedOption:null})
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
        ;

    }
    deleteProduction=()=>{
        this.client.DeleteProduction(this.state.idDeleteProduction)
            .then((response) => {
                if(response.data.code==200){
                    this.getProductionsByProduct()
                    notify(
                        'Success',
                        'The Production was successfully deleted',
                        {state:"success"}
                    )
                }else if(response.data.code==404){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }else if(response.data.code==400){
                    notify(
                        'Warning',
                        response.data.message,
                        {state:"warning"}
                    )
                }
                this.handleToggleModalDeleteProduction()
            })
            .catch((error) => {
                notify(
                    'Error',
                    'Something went wrong!',
                    {state:"error"}
                )            })
        ;

    }


    handleChangeSelect = (selectedOption) =>{
        const self=this
        this.setState({selectedOption:selectedOption}, function (){
            self.getProductionsByProduct()
        });
    }

    tableBody=()=>{
        if(this.state.productions){
            const tableB=this.state.productions.map((production)=>{
                return(
                    <tr key={production.productionId}>
                        <td>
                            {production.date}
                        </td>
                        <td style={{textAlign:"right"}}>{production.qta+' Kg'}</td>
                        <td>{production.note}</td>
                        <td>
                            <IconButton onClick={()=>this.handleToggleModalEditProduction(production.productionId)} >
                                <Icon icon='it-pencil' size="sm" />
                            </IconButton>
                        </td>
                        <td>
                            <IconButton onClick={()=>this.handleToggleModalDeleteProduction(production.productionId)}>
                                <Icon icon='it-delete' size="sm" />
                            </IconButton>
                        </td>
                    </tr>
                )
            })
            return tableB
        }else{
            return (
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                    </td>
                    <td>
                    </td>
                </tr>
            )
        }
    }
    handleToggleModalNewProduct=()=>{
        if(this.state.openModalNewProduct){
            this.setState({
                openModalNewProduct:false,
                nameNewProduct:"",
                typeNewProduct:"",
                specificsNewProduct:"",
            })
        }else {
            this.setState({
                openModalNewProduct:true,
            })
        }
    }
    handleToggleModalEditProduct=()=>{
        if(this.state.openModalEditProduct){
            this.setState({
                openModalEditProduct:false,
                nameEditProduct:"",
                typeEditProduct:"",
                specificsEditProduct:"",
            })
        }else {
            const product=this.state.products.find(product=> product.productId===this.state.selectedOption.value)

            this.setState({
                openModalEditProduct:true,
                nameEditProduct:product.product,
                typeEditProduct:product.type,
                specificsEditProduct:product.specifics,
            })
        }
    }
    handleToggleModalNewProduction=()=>{
        if(this.state.openModalNewProduction){
            this.setState({
                openModalNewProduction:false,
                amountNewProduction:"",
                noteNewProduction:"",
            })
        }else {
            this.setState({
                openModalNewProduction:true,
            })
        }
    }
    handleToggleModalEditProduction=(id)=>{
        if(this.state.openModalEditProduction){
            this.setState({
                openModalEditProduction:false,
                idEditProduction:"",
                amountEditProduction:"",
                noteEditProduction:"",
            })
        }else {
            const production=this.state.productions.find(production=>production.productionId===id)
            this.setState({
                openModalEditProduction:true,
                idEditProduction:id,
                amountEditProduction:production.qta,
                noteEditProduction:production.note,
            })
        }
    }
    handleToggleModalDeleteProduction=(id)=>{
        if(this.state.openModalDeleteProduction){
            this.setState({
                openModalDeleteProduction:false,
                idDeleteProduction:"",
            })
        }else {
            this.setState({
                openModalDeleteProduction:true,
                idDeleteProduction:id,
            })
        }
    }


    handleChange = (name) => event => {
        this.setState({
            [name]: event.target.value,
        });
    };
    addProductionButton=()=>{
        if(this.state.selectedOption){
            return(
                <Fab variant="extended" color="primary" aria-label="add"
                     onClick={() => this.handleToggleModalNewProduction()}
                >
                    <Icon color='white' icon='it-plus' />
                    Add a production
                </Fab>
            )
        }
    }
    modifyProductButton=()=>{
        if(this.state.selectedOption){
            return(
                <Fab variant="extended" color="primary" aria-label="add"
                     onClick={() => this.handleToggleModalEditProduct()}
                     style={{marginRight:20}}
                >
                    <Icon color='white' icon='it-pencil' />
                    Edit the product
                </Fab>
            )
        }
    }
    deleteProductButton=()=>{
        if(this.state.selectedOption){
            return(
                <Fab variant="extended" color="primary" aria-label="add"
                     onClick={() => this.setState({openModalDeleteProduct:!this.state.openModalDeleteProduct})}
                     style={{marginRight:20}}
                >
                    <Icon color='white' icon='it-delete' />
                    Delete the product
                </Fab>
            )
        }
    }

    render() {
        return(
            <div>
                <Container style={{paddingTop:20,paddingBottom:20}}>
                    <h4 style={{fontSize:35}}>Production</h4>

                    <div className='bootstrap-select-wrapper' style={{marginTop:50,marginBottom:20}}>
                        <label htmlFor='selectExampleReset'>Product</label>
                        <Select
                            id='selectExampleReset'
                            value={this.state.selectedOption}
                            onChange={this.handleChangeSelect}
                            options={this.state.productsForSelect}
                            placeholder='Choose a product'
                            aria-label='Choose a product'
                        />
                    </div>

                    <div style={{textAlign:"right"}}>
                        <Fab variant="extended" color="primary" aria-label="add" style={{marginRight:20}}
                             onClick={() => this.handleToggleModalNewProduct()}
                        >
                            <Icon color='white' icon='it-plus' />
                            Add a new product
                        </Fab>
                        {this.modifyProductButton()}
                        {this.deleteProductButton()}
                        {this.addProductionButton()}
                    </div>



                    <Table>
                        <thead>
                        <tr>
                            <th scope='col'>Date</th>
                            <th scope='col' style={{textAlign:"right"}}>Q.ta</th>
                            <th scope='col' width={'50%'} >Note</th>
                            <th scope='col' >Modify</th>
                            <th scope='col' >Remove</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.tableBody()}
                        </tbody>
                    </Table>
                    <div>
                        <Modal
                            isOpen={this.state.openModalNewProduct}
                            toggle={() => this.handleToggleModalNewProduct()}
                            centered
                            labelledBy='esempio9'
                        >
                            <ModalHeader toggle={() => this.handleToggleModalNewProduct()} id='essempio9'>
                                Add a new product
                            </ModalHeader>
                            <ModalBody>
                                <div className='form-row'>
                                    <Input
                                        type='text'
                                        label='Name'
                                        id='inputTitle'
                                        value={this.state.nameNewProduct}
                                        onChange={this.handleChange("nameNewProduct")}
                                        wrapperClass='col col-md-12'
                                    />
                                </div>
                                <div className='form-row'>
                                    <Input
                                        type='text'
                                        label='Type'
                                        id='inputTitle'
                                        value={this.state.typeNewProduct}
                                        onChange={this.handleChange("typeNewProduct")}

                                        wrapperClass='col col-md-12'
                                    />
                                </div>
                                <div>
                                    <TextArea
                                        label='Specifics'
                                        rows={3}
                                        style={{resize:"none"}}
                                        value={this.state.specificsNewProduct}
                                        onChange={this.handleChange("specificsNewProduct")}
                                    />
                                </div>
                            </ModalBody>
                            <ModalFooter>
                                <Button color='secondary' onClick={() => this.handleToggleModalNewProduct()}>
                                    Close
                                </Button>
                                <Button color='primary' onClick={() => this.postProduct()}>
                                    OK
                                </Button>
                            </ModalFooter>
                        </Modal>
                        <Modal
                            isOpen={this.state.openModalNewProduction}
                            toggle={() => this.handleToggleModalNewProduction()}
                            centered
                            labelledBy='esempio9'
                        >
                            <ModalHeader toggle={() => this.handleToggleModalNewProduction()} id='essempio9'>
                                Add a production ({this.state.selectedOption?this.state.selectedOption.label:""})
                            </ModalHeader>
                            <ModalBody>
                                <div className='form-row'>
                                    <Input
                                        type='number'
                                        label='Amount (Kg)'
                                        id='inputTitle'
                                        value={this.state.amountNewProduction}
                                        onChange={this.handleChange("amountNewProduction")}

                                        wrapperClass='col col-md-12'
                                    />
                                </div>
                                <div>
                                    <TextArea
                                        label='Note'
                                        rows={4}
                                        style={{resize:"none"}}
                                        value={this.state.noteNewProduction}
                                        onChange={this.handleChange("noteNewProduction")}
                                    />
                                </div>
                            </ModalBody>
                            <ModalFooter>
                                <Button color='secondary' onClick={() => this.handleToggleModalNewProduction()}>
                                    Close
                                </Button>
                                <Button color='primary' onClick={() => this.postProduction()}>
                                    OK
                                </Button>
                            </ModalFooter>
                        </Modal>
                        <Modal
                            isOpen={this.state.openModalEditProduct}
                            toggle={() => this.handleToggleModalEditProduction()}
                            centered
                            labelledBy='esempio9'
                        >
                            <ModalHeader toggle={() => this.handleToggleModalEditProduct()} id='essempio9'>
                                Edit the product ({this.state.selectedOption?this.state.selectedOption.label:""})
                            </ModalHeader>
                            <ModalBody>
                                <div className='form-row'>
                                    <Input
                                        type='text'
                                        label='Name'
                                        id='inputTitle'
                                        value={this.state.nameEditProduct}
                                        onChange={this.handleChange("nameEditProduct")}
                                        wrapperClass='col col-md-12'
                                    />
                                </div>
                                <div className='form-row'>
                                    <Input
                                        type='text'
                                        label='Type'
                                        id='inputTitle'
                                        value={this.state.typeEditProduct}
                                        onChange={this.handleChange("typeEditProduct")}

                                        wrapperClass='col col-md-12'
                                    />
                                </div>
                                <div>
                                    <TextArea
                                        label='Specifics'
                                        rows={3}
                                        style={{resize:"none"}}
                                        value={this.state.specificsEditProduct}
                                        onChange={this.handleChange("specificsEditProduct")}
                                    />
                                </div>
                            </ModalBody>
                            <ModalFooter>
                                <Button color='secondary' onClick={() => this.handleToggleModalEditProduct()}>
                                    Close
                                </Button>
                                <Button color='primary' onClick={() => this.putProduct()}>
                                    OK
                                </Button>
                            </ModalFooter>
                        </Modal>
                        <Modal
                            isOpen={this.state.openModalEditProduction}
                            toggle={() => this.handleToggleModalEditProduction()}
                            centered
                            labelledBy='esempio9'
                        >
                            <ModalHeader toggle={() => this.handleToggleModalEditProduction()} id='essempio9'>
                                Edit the production
                            </ModalHeader>
                            <ModalBody>
                                <div className='form-row'>
                                    <Input
                                        type='number'
                                        label='Amount (Kg)'
                                        id='inputTitle'
                                        value={this.state.amountEditProduction}
                                        onChange={this.handleChange("amountEditProduction")}

                                        wrapperClass='col col-md-12'
                                    />
                                </div>
                                <div>
                                    <TextArea
                                        label='Note'
                                        rows={4}
                                        style={{resize:"none"}}
                                        value={this.state.noteEditProduction}
                                        onChange={this.handleChange("noteEditProduction")}
                                    />
                                </div>
                            </ModalBody>
                            <ModalFooter>
                                <Button color='secondary' onClick={() => this.handleToggleModalEditProduction()}>
                                    Close
                                </Button>
                                <Button color='primary' onClick={() => this.putProduction()}>
                                    OK
                                </Button>
                            </ModalFooter>
                        </Modal>
                        <Modal
                            isOpen={this.state.openModalDeleteProduction}
                            toggle={() => this.handleToggleModalDeleteProduction()}
                            centered
                            labelledBy='esempio9'
                        >
                            <ModalHeader  id='essempio9'>
                                Are you sure you want to delete?
                            </ModalHeader>
                            <ModalFooter>
                                <Button color='secondary' onClick={() => this.handleToggleModalDeleteProduction()}>
                                    Close
                                </Button>
                                <Button color='primary' onClick={() => this.deleteProduction()}>
                                    OK
                                </Button>
                            </ModalFooter>
                        </Modal>
                        <Modal
                            isOpen={this.state.openModalDeleteProduct}
                            toggle={() => this.setState({openModalDeleteProduct:!this.state.openModalDeleteProduct})}
                            centered
                            labelledBy='esempio9'
                        >
                            <ModalHeader  id='essempio9'>
                                Are you sure you want to delete? ({this.state.selectedOption?this.state.selectedOption.label:""})
                            </ModalHeader>
                            <ModalFooter>
                                <Button color='secondary' onClick={() => this.setState({openModalDeleteProduct:!this.state.openModalDeleteProduct})}>
                                    Close
                                </Button>
                                <Button color='primary' onClick={() => this.deleteProduct()}>
                                    OK
                                </Button>
                            </ModalFooter>
                        </Modal>
                    </div>

                </Container>

            </div>

        )
    }


}