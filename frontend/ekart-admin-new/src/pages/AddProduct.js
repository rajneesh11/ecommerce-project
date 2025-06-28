import React, {useState} from "react";
import {NavLink} from "react-router-dom";
import Icon from "../components/Icon";
import PageTitle from "../components/Typography/PageTitle";
import {HomeIcon, ModalsIcon, PublishIcon, StoreIcon} from "../icons";
import axios from "axios";
import {
    Button,
    Card,
    CardBody,
    Input,
    Label,
    Modal,
    ModalBody,
    ModalFooter,
    ModalHeader,
    Select,
    Textarea,
} from "@windmill/react-ui";
import { API_BASE_URL } from "../utils/config";

const FormTitle = ({children}) => {
    return (
        <h2 className="mb-3 text-sm font-semibold text-gray-600 dark:text-gray-300">
            {children}
        </h2>
    );
};

const AddProduct = () => {
    const [errors, setErrors] = useState({});


    const [product, setProductData] = useState({
        name: "",
        description: "",
        category: "",
        brand: "",
        // dimension: "",
        price: 0,
        weight: 0,
        rating: 0,
        stockQuantity: 0,
        published: false,
        imageUrl: ""
    });

    const [productImage, setProductImage] = useState(null);

    const handleChange = (e) => {
        const {name, value, files} = e.target;
        if (name === 'image') {
            setProductImage(files[0]);
        } else {
            setProductData((prev) => ({
                ...prev,
                [name]: value,
            }));
        }
    };

    const validateForm = () => {
        let newErrors = {};

        if (!product.name.trim()) newErrors.name = "Product name is required";
        if (!product.price || isNaN(product.price))
            newErrors.price = "Valid price is required";
        if (!product.description.trim())
            newErrors.description = "Description is required";
        if (!product.stockQuantity || isNaN(product.stockQuantity))
            newErrors.stockQuantity = "Valid stock quantity is required";
        if (!product.weight || isNaN(product.weight))
            newErrors.weight = "Valid weight is required";
        if (!product.brand.trim()) newErrors.brand = "Brand is required";
        // if (!product.dimension.trim())
        //   newErrors.dimension = "Dimension is required";
        if (!product.category.trim() || product.category.trim() === "--Select--")
            newErrors.category = "Category is required";
        if (!productImage) newErrors.image = "Product image is required";

        setErrors(newErrors);

        return Object.keys(newErrors).length === 0;
    };

    const handleCategoryChange = (e) => {
        e.preventDefault();
        product.category = e.target.value;
    };

    const handleProductImageUpload = async () => {
        if (validateForm()) {
            const formData = new FormData();
            formData.append('image', productImage);
            const jwtToken = localStorage.getItem("jwtToken");

            try {
                return await axios.post(
                    `${API_BASE_URL}/api/images/upload`,
                    formData,
                    {
                        headers: {
                            'Content-Type': 'multipart/form-data',
                            Authorization: `Bearer ${jwtToken}`,
                        },
                    }
                );
            } catch (error) {
                console.error("Error uploading image:", error);
                alert("Error uploading image");
            }
        }
    }

    const handleProductSave = async (product) => {
        product.price = parseFloat(product.price);
        product.weight = parseFloat(product.weight);
        product.stockQuantity = parseInt(product.stockQuantity);
        console.log("handleProductSave", product);

        if (validateForm()) {
            const jwtToken = localStorage.getItem("jwtToken");

            const response = await axios.post(
                `${API_BASE_URL}/api/product/addProduct`,
                product,
                {
                    headers: {
                        Authorization: `Bearer ${jwtToken}`,
                    },
                }
            ).then((res) => {
                console.log(res);
                if (res.status === 200) {
                    openModal();
                } else {
                    alert("Error adding product");
                }
            });
        } else console.log("form not valid");
    };
    const handleSaveDraft = (e) => {
        e.preventDefault();
        product.published = false;
        handleProductImageUpload().then((res) => {
            product.imageUrl = res.data
            handleProductSave(product)
        });
    };
    const handlePublishProduct = (e) => {
        e.preventDefault();
        product.published = true;
        handleProductImageUpload().then((res) => {
            product.imageUrl = res.data
            handleProductSave(product)
        });
    };
    const [isModalOpen, setIsModalOpen] = useState(false);

    async function openModal() {
        setIsModalOpen(true);
    }

    function closeModal() {
        setIsModalOpen(false);
        window.location.href = "/app/all-products";
    }

    return (
        <div>
            <PageTitle>Add New Product</PageTitle>

            {/* Breadcum */}
            <div className="flex text-gray-800 dark:text-gray-300">
                <div className="flex items-center text-purple-600">
                    <Icon className="w-5 h-5" aria-hidden="true" icon={HomeIcon}/>
                    <NavLink exact to="/app/dashboard" className="mx-2">
                        Dashboard
                    </NavLink>
                </div>
                {">"}
                <p className="mx-2">Add new Product</p>
            </div>

            <div className="w-full mt-8 grid gap-4 grid-col md:grid-cols-3 ">
                <Card className="row-span-2 md:col-span-2">
                    <CardBody>
                        <FormTitle>Product Image</FormTitle>
                        <input
                            type="file"
                            name="image"
                            accept="image/*"
                            className="mb-4 text-gray-800 dark:text-gray-300"
                            placeholder="Upload product image"
                            // value={product.image}
                            onChange={handleChange}
                        />
                        {errors.image && (
                            <p className="text-red-500 text-sm">{errors.image}</p>
                        )}

                        <FormTitle>Product Name</FormTitle>
                        <Label>
                            <div className="mb-4">
                                <Input
                                    name="name"
                                    placeholder="Type product name here"
                                    value={product.name}
                                    onChange={handleChange}
                                />
                                {errors.name && (
                                    <p className="text-red-500 text-sm">{errors.name}</p>
                                )}
                            </div>
                        </Label>

                        <FormTitle>Product Price</FormTitle>
                        <Label>
                            <div className="mb-4">
                                <Input
                                    name="price"
                                    type="number"
                                    placeholder="Enter product price here"
                                    value={product.price}
                                    onChange={handleChange}
                                />
                                {errors.price && (
                                    <p className="text-red-500 text-sm">{errors.price}</p>
                                )}
                            </div>
                        </Label>

                        <FormTitle>Short description</FormTitle>
                        <Label>
                            <div className="mb-4">
                                <Textarea
                                    name="description"
                                    rows="3"
                                    placeholder="Enter product short description here"
                                    value={product.description}
                                    onChange={handleChange}
                                />
                                {errors.description && (
                                    <p className="text-red-500 text-sm">{errors.description}</p>
                                )}
                            </div>
                        </Label>

                        <FormTitle>Stock Qunatity</FormTitle>
                        <Label>
                            <div className="mb-4">
                                <Input
                                    name="stockQuantity"
                                    placeholder="Enter product stock quantity"
                                    value={product.stockQuantity}
                                    type="number"
                                    onChange={handleChange}
                                />
                                {errors.stockQuantity && (
                                    <p className="text-red-500 text-sm">{errors.stockQuantity}</p>
                                )}
                            </div>
                        </Label>

                        <FormTitle>Weight</FormTitle>
                        <Label>
                            <div className="mb-4">
                                <Input
                                    name="weight"
                                    placeholder="Enter product weight"
                                    value={product.weight}
                                    type="number"
                                    onChange={handleChange}
                                />
                                {errors.weight && (
                                    <p className="text-red-500 text-sm">{errors.weight}</p>
                                )}
                            </div>
                        </Label>

                        <FormTitle>Brand</FormTitle>
                        <Label>
                            <div className="mb-4">
                                <Input
                                    name="brand"
                                    placeholder="Enter product brand"
                                    value={product.brand}
                                    onChange={handleChange}
                                />
                                {errors.brand && (
                                    <p className="text-red-500 text-sm">{errors.brand}</p>
                                )}
                            </div>
                        </Label>
                        {/* <FormTitle>Product Dimension</FormTitle>
            <Label>
              <div className="mb-4">
                <Input
                  placeholder="Enter product brand"
                  value={product.dimension}
                  onChange={handleChange}
                />
                {errors.dimension && (
                  <p className="text-red-500 text-sm">{errors.dimension}</p>
                )}
              </div>
            </Label> */}
                    </CardBody>
                </Card>

                <Card className="h-48">
                    <CardBody>
                        <div className="flex mb-8">
                            <Button
                                layout="primary"
                                className="mr-3"
                                iconLeft={PublishIcon}
                                onClick={handlePublishProduct}
                            >
                                Publish
                            </Button>
                            <Button
                                layout="link"
                                iconLeft={StoreIcon}
                                onClick={handleSaveDraft}
                            >
                                Save as Draft
                            </Button>
                        </div>
                        <Label className="mt-4">
                            <FormTitle>Select Product Category</FormTitle>
                            <Select
                                className="mt-1"
                                onChange={handleCategoryChange}
                                name="category"
                            >
                                <option>--Select--</option>
                                <option>Electronic</option>
                                <option>Fashion</option>
                                <option>Cosmatics</option>
                                <option>Food and Meal</option>
                            </Select>
                            {errors.category && (
                                <p className="text-red-500 text-sm">{errors.category}</p>
                            )}
                        </Label>
                    </CardBody>
                </Card>
            </div>
            <Modal isOpen={isModalOpen} onClose={closeModal}>
                <ModalHeader className="flex items-center">
                    {/* <div className="flex items-center"> */}
                    <Icon icon={ModalsIcon} className="w-6 h-6 mr-3"/>
                    Add Product
                    {/* </div> */}
                </ModalHeader>
                <ModalBody>
                    Product Added Successfully
                </ModalBody>
                <ModalFooter>
                    <div className="hidden sm:block">
                        <Button onClick={closeModal}>OK</Button>
                    </div>
                    <div className="block w-full sm:hidden">
                        <Button block size="large" onClick={closeModal}>
                            OK
                        </Button>
                    </div>
                </ModalFooter>
            </Modal>
        </div>
    );
};

export default AddProduct;
