package com.ezee.store.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezee.store.config.JdbcConfig;
import com.ezee.store.dto.EmployeeDetailDTO;
import com.ezee.store.exception.ErrorCode;
import com.ezee.store.exception.ServiceException;

@Repository
public class EmployeeDetailsRepository {
	@Autowired
	private JdbcConfig jdbcConfig;
	
	
	public static final Logger LOGGER = LogManager.getLogger("com.ezee.store.repository");
	public boolean existsById(int id) {
	    DataSource dataSource = jdbcConfig.getConnection();
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    boolean exists = false;

	    try {
	        connection = dataSource.getConnection();
	        String query = "SELECT COUNT(employee_detail_id) FROM employee_detail WHERE employee_detail_id = ?";
	        statement = connection.prepareStatement(query);
	        statement.setInt(1, id);

	        resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            int count = resultSet.getInt(1);
	            exists = count > 0;
	        }
	    } catch (SQLException e) {
	        LOGGER.error("Error in existsById: {}/n, data: {}", e.getMessage(), e);
	    } finally {
	        if (resultSet != null) {
	            try {
	                resultSet.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return exists;
	}
	
	public List<EmployeeDetailDTO> fetchAllEmployee() {
		DataSource dataSource = jdbcConfig.getConnection();
		Connection connection = null;
		Statement statement = null;
		List<EmployeeDetailDTO> list = jdbcConfig.getList();
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();

			String query = "SELECT employee_detail_id, employee_name, gender, date_of_birth, email, phone_number, hire_date, salary, address, status, updated_at from employee_detail";   
			resultSet = statement.executeQuery(query);

			for (; resultSet.next();) {
				EmployeeDetailDTO employeeDetailDto=new EmployeeDetailDTO();
				employeeDetailDto.setEmployeeDetailId(resultSet.getInt("employee_detail_id"));
				employeeDetailDto.setEmployeeName(resultSet.getString("employee_name"));
				employeeDetailDto.setGender(resultSet.getString("gender"));
				employeeDetailDto.setDateOfBirth(resultSet.getString("date_of_birth"));
				employeeDetailDto.setEmail(resultSet.getString("email"));
				employeeDetailDto.setPhoneNo(resultSet.getLong("phone_number"));
				employeeDetailDto.setHireDate(resultSet.getDate("hire_date"));
				employeeDetailDto.setSalary(resultSet.getDouble("salary"));
				employeeDetailDto.setAddress(resultSet.getString("address"));
				employeeDetailDto.setStatus(resultSet.getString("status"));
				employeeDetailDto.setUpdated_at(resultSet.getDate("updated_at"));
				list.add(employeeDetailDto);
			}
			System.out.println("executed customer repo. fetchall");
			
			if (list.isEmpty()) {
				throw new ServiceException(ErrorCode.EMPTY_RESULT_DATA_EXCEPTION);
			}

		} catch (SQLException e) {
			LOGGER.error("Error occuring on message: {}/n, data: {}", e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}

		return list;
	}
	public EmployeeDetailDTO fetchById(int id) {
		DataSource dataSource = jdbcConfig.getConnection();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		EmployeeDetailDTO employeeDetailDto = null;

		try {
			connection = dataSource.getConnection();

			String query = "SELECT employee_detail_id, employee_name, gender, date_of_birth, email, phone_number, hire_date, salary, address, status, updated_at from employee_detail";   

			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			
			resultSet = statement.executeQuery();
			

			if (resultSet != null) {
				if (resultSet.next()) {
				    employeeDetailDto=new EmployeeDetailDTO();
					employeeDetailDto.setEmployeeDetailId(resultSet.getInt("employee_detail_id"));
					employeeDetailDto.setEmployeeName(resultSet.getString("employee_name"));
					employeeDetailDto.setGender(resultSet.getString("gender"));
					employeeDetailDto.setDateOfBirth(resultSet.getString("date_of_birth"));
					employeeDetailDto.setEmail(resultSet.getString("email"));
					employeeDetailDto.setPhoneNo(resultSet.getLong("phone_number"));
					employeeDetailDto.setHireDate(resultSet.getDate("hire_date"));
					employeeDetailDto.setSalary(resultSet.getDouble("salary"));
					employeeDetailDto.setAddress(resultSet.getString("address"));
					employeeDetailDto.setStatus(resultSet.getString("status"));
					employeeDetailDto.setUpdated_at(resultSet.getDate("updated_at"));
					
					
				} else {
					throw new ServiceException(ErrorCode.ID_NOT_FOUND_EXCEPTION);
				}				
			}

			System.out.println("fetchbyId executed.");
			
		} catch (SQLException e) {
			LOGGER.error("Error occuring on message: {}/n, data: {}", e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return employeeDetailDto;
	}
	public int delete(int id) {
		DataSource dataSource = jdbcConfig.getConnection();
		Connection connection = null;
		PreparedStatement statement = null;
		int affected = 0;

		try {
			connection = dataSource.getConnection();
			String query = "DELETE FROM employee_detail WHERE employee_detail_id = ?";

			statement = connection.prepareStatement(query);
			statement.setInt(1, id);

			affected = statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.info("Error occuring on message: {}/n, data: {}", e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return affected;
	}
	public int update(EmployeeDetailDTO employeeDetailDto) {
		DataSource dataSource = jdbcConfig.getConnection();
		Connection connection = null;
		PreparedStatement statement = null;
		int executeUpdate =0;
		
		try {
			connection = dataSource.getConnection();
			String query = "UPDATE employee_detail SET employee_detail_id=?, employee_name=?, gender=?, date_of_birth=?, email=?, phone_number=?, hire_date=?, salary=?, address=?, status=?, updated_at=?  where employee_detail_id=?";
			
			statement = connection.prepareStatement(query);
			statement.setInt(1, employeeDetailDto.getEmployeeDetailId());
			statement.setString(2, employeeDetailDto.getEmployeeName());
			statement.setString(3, employeeDetailDto.getGender());
			statement.setString(4, employeeDetailDto.getDateOfBirth());
			statement.setString(5, employeeDetailDto.getEmail());
			statement.setLong(6, employeeDetailDto.getPhoneNo());
			statement.setDate(7, employeeDetailDto.getHireDate());
			statement.setDouble(8, employeeDetailDto.getSalary());
			statement.setString(9, employeeDetailDto.getAddress());
			statement.setString(10, employeeDetailDto.getStatus());
			statement.setDate(11, employeeDetailDto.getUpdated_at());
			executeUpdate = statement.executeUpdate();
			System.out.println("update executed.");

		} catch (SQLException e) {
			LOGGER.info("Error occuring on message: {}/n, data: {}", e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return executeUpdate;
		
	}
	public int addProduct(EmployeeDetailDTO employeeDetailDto) {
		DataSource dataSource = jdbcConfig.getConnection();
		Connection connection = null;
		PreparedStatement statement = null;
		int executeUpdate = 0;

		try {
			if (existsById(employeeDetailDto.getEmployeeDetailId())) {
	            throw new ServiceException(ErrorCode.ID_ALREADY_EXISTS);
	        }
			connection = dataSource.getConnection();
			
			String query = "INSERT INTO employee_detail (employee_detail_id, employee_name, gender, date_of_birth, email, phone_number, hire_date, salary, address, status, updated_at ) VALUES ( ?, ?, ?, ?, ?, ?, ? ?, ?, ?, ?)";
			statement = connection.prepareStatement(query);
			statement.setInt(1, employeeDetailDto.getEmployeeDetailId());
			statement.setString(2, employeeDetailDto.getEmployeeName());
			statement.setString(3, employeeDetailDto.getGender());
			statement.setString(4, employeeDetailDto.getDateOfBirth());
			statement.setString(5, employeeDetailDto.getEmail());
			statement.setLong(6, employeeDetailDto.getPhoneNo());
			statement.setDate(7, employeeDetailDto.getHireDate());
			statement.setDouble(8, employeeDetailDto.getSalary());
			statement.setString(9, employeeDetailDto.getAddress());
			statement.setString(10, employeeDetailDto.getStatus());
			statement.setDate(11, employeeDetailDto.getUpdated_at());
			executeUpdate = statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error("Error occuring on message: {}/n, data: {}", e.getMessage(), e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		return executeUpdate;
	}
}




