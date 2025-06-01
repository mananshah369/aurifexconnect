import { useState } from "react";
import { motion } from "framer-motion";
import {
  FiFilter,
  FiDownload,
  FiPlusCircle,
  FiChevronDown,
  FiSearch,
} from "react-icons/fi";

function Inventories() {
  //////OpenFilter States
  const [filterOpen, setFilterOpen] = useState(false);

  ///////Modal States
  const [showAddModal, setShowAddModal] = useState(false);
  const [name, setName] = useState("");
  const [price, setPrice] = useState("");

  ////////////Filters States
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedId, setSelectedId] = useState("");
  const [selectedName, setSelectedName] = useState("");
  const [selectedBranch, setSelectedBranch] = useState("");
  const [inventories] = useState([
    {
      id: 1,
      name: "Laptop",
      price: "₹85,000",
      quantity: 2,
      branch: "Bangalore",
    },
    {
      id: 2,
      name: "Smartphone",
      price: "₹25,999",
      quantity: 5,
      branch: "Delhi",
    },
    {
      id: 3,
      name: "Headphones",
      price: "₹2,499",
      quantity: 10,
      branch: "Mumbai",
    },
    {
      id: 4,
      name: "Office Chair",
      price: "₹7,899",
      quantity: 4,
      branch: "Pune",
    },
    {
      id: 5,
      name: "Monitor",
      price: "₹12,750",
      quantity: 3,
      branch: "Hyderabad",
    },
  ]);

  const uniqueIds = [...new Set(inventories.map((item) => item.id))];
  const uniqueNames = [...new Set(inventories.map((item) => item.name))];
  const uniqueBranches = [...new Set(inventories.map((item) => item.branch))];

  ////////////////////Search Functionality////////////////

  const filteredInventories = inventories.filter((item) => {
    const matchesName = item.name
      .toLowerCase()
      .includes(searchTerm.toLowerCase());

    const matchesId = selectedId === "" || item.id === parseInt(selectedId);
    console.log(matchesId);

    const matchesNames = selectedName === "" || item.name === selectedName;
    // console.log(matchesNames);

    const matchesBranch =
      selectedBranch === "" || item.branch === selectedBranch;
    // console.log(matchesBranch);

    return matchesName && matchesId && matchesNames && matchesBranch;
  });

  //////////////////// Modal Functionality////////////////////////

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Submitted", { name, price });
  };

  const pageVariants = {
    initial: { opacity: 0, y: 20 },
    animate: { opacity: 1, y: 0 },
  };

  return (
    <motion.div
      variants={pageVariants}
      initial="initial"
      animate="animate"
      transition={{ duration: 0.5 }}
    >
      {/* Headers Above Table */}
      <div className="md:flex md:items-center md:justify-between mb-6">
        <div className="flex-1 min-w-0">
          <h1 className="text-2xl font-bold leading-7 text-neutral-900 sm:text-3xl sm:leading-9 sm:truncate">
            Inventories
          </h1>
        </div>
        <div className="mt-4 flex md:mt-0 md:ml-4">
          <button
            className="btn btn-secondary mr-3 flex items-center"
            onClick={() => setFilterOpen(!filterOpen)}
          >
            <FiFilter className="mr-2 h-4 w-4" />
            Filter
            <FiChevronDown
              className={`ml-2 h-4 w-4 transition-transform ${
                filterOpen ? "rotate-180" : ""
              }`}
            />
          </button>
          <button className="btn btn-secondary mr-3">
            <FiDownload className="mr-2 h-4 w-4" />
            Export
          </button>
          <button
            className="btn btn-primary flex items-center"
            onClick={() => setShowAddModal(true)}
          >
            <FiPlusCircle className="mr-2 h-4 w-4" />
            Add Item
          </button>
        </div>
      </div>

      {/* Search and filters */}
      <div className="mb-6">
        <div className="relative">
          <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
            <FiSearch className="h-5 w-5 text-neutral-400" />
          </div>
          <input
            type="text"
            className="input pl-10"
            placeholder="Search Inventories..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>

        {filterOpen && (
          <motion.div
            initial={{ opacity: 0, y: -10 }}
            animate={{ opacity: 1, y: 0 }}
            className="mt-4 grid grid-cols-1 md:grid-cols-4 gap-4"
          >
            <div>
              <label className="block text-sm font-medium text-neutral-700 mb-1">
                Id
              </label>
              <select
                className="input"
                value={selectedId}
                onChange={(e) => setSelectedId(e.target.value)}
              >
                <option value="">All</option>
                {uniqueIds.map((id) => (
                  <option key={id} value={id}>
                    {id}
                  </option>
                ))}
              </select>
            </div>
            <div>
              <label className="block text-sm font-medium text-neutral-700 mb-1">
                Name
              </label>
              <select
                className="input"
                value={selectedName}
                onChange={(e) => setSelectedName(e.target.value)}
              >
                <option value="">All Name</option>
                {uniqueNames.map((name) => (
                  <option key={name} value={name}>
                    {name}
                  </option>
                ))}
              </select>
            </div>
            <div>
              <label className="block text-sm font-medium text-neutral-700 mb-1">
                Branch
              </label>
              <select
                className="input"
                value={selectedBranch}
                onChange={(e) => setSelectedBranch(e.target.value)}
              >
                <option value="">All Name</option>
                {uniqueBranches.map((branch) => (
                  <option key={branch} value={branch}>
                    {branch}
                  </option>
                ))}
              </select>
            </div>
          </motion.div>
        )}
      </div>

      {/* Transactions table */}
      <div className="card overflow-hidden">
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-neutral-200">
            <thead className="bg-neutral-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  ID
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  NAME
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  PRICE
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Quantity
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-neutral-500 uppercase tracking-wider">
                  Branch
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-neutral-200">
              {filteredInventories.map((transaction) => (
                <motion.tr
                  key={transaction.id}
                  whileHover={{ backgroundColor: "#f9fafb" }}
                  className="cursor-pointer"
                >
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500">
                    {transaction.id}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-neutral-900">
                    {transaction.name}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500 capitalize">
                    {transaction.price}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500">
                    {transaction.quantity}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-neutral-500">
                    {transaction.branch}
                  </td>
                </motion.tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {/* Add Transaction Modal */}
      {showAddModal && (
        <form onSubmit={handleSubmit}>
          <div className="fixed inset-0 z-50 overflow-y-auto">
            <div className="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
              <div
                className="fixed inset-0 transition-opacity"
                aria-hidden="true"
              >
                <div className="absolute inset-0 bg-neutral-500 opacity-75"></div>
              </div>

              <motion.div
                initial={{ opacity: 1, scale: 0.95, y: 20 }}
                className="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full"
              >
                <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                  <div className="sm:flex sm:items-start">
                    <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left w-full">
                      <h3 className="text-lg leading-6 font-medium text-neutral-900 mb-4">
                        Add New Item
                      </h3>

                      <div className="mt-4 space-y-4">
                        <div>
                          <label className="block text-sm font-medium text-neutral-700 mb-1">
                            Name
                          </label>
                          <input
                            type="text"
                            className="input"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                          />
                        </div>

                        <div>
                          <label className="block text-sm font-medium text-neutral-700 mb-1">
                            Price
                          </label>
                          <input
                            type="number"
                            step="0.01"
                            className="input"
                            value={price}
                            onChange={(e) => setPrice(e.target.value)}
                          />
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <div className="bg-neutral-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                  <button
                    type="submit"
                    className="btn btn-primary w-full sm:w-auto sm:ml-3"
                  >
                    Add Item
                  </button>
                  <button
                    type="button"
                    className="btn btn-secondary w-full sm:w-auto mt-3 sm:mt-0"
                    onClick={() => setShowAddModal(false)}
                  >
                    Cancel
                  </button>
                </div>
              </motion.div>
            </div>
          </div>
        </form>
      )}
    </motion.div>
  );
}

export default Inventories;
